/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.download.service

import io.ktor.client.request.request
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.Path
import okio.buffer
import tachiyomi.core.di.Inject
import tachiyomi.core.io.DataUriStringSource
import tachiyomi.core.io.nameWithoutExtension
import tachiyomi.core.io.peek
import tachiyomi.core.io.saveTo
import tachiyomi.core.util.ImageUtil
import tachiyomi.domain.catalog.interactor.GetLocalCatalog
import tachiyomi.domain.download.model.QueuedDownload
import tachiyomi.source.HttpSource
import tachiyomi.source.model.ChapterInfo
import tachiyomi.source.model.ImageBase64
import tachiyomi.source.model.ImageUrl
import tachiyomi.source.model.Page
import tachiyomi.source.model.PageComplete
import tachiyomi.source.model.PageListEmpty
import tachiyomi.source.model.PageUrl
import tachiyomi.source.model.Text

internal open class Downloader @Inject constructor(
  private val directoryProvider: DownloadDirectoryProvider,
  private val getLocalCatalog: GetLocalCatalog,
  private val fileSystem: FileSystem
) {

  protected open val retryDelay = 1000L

  fun worker(
    scope: CoroutineScope,
    downloads: ReceiveChannel<QueuedDownload>,
    downloadResult: SendChannel<Result>
  ) = scope.launch {
    for (download in downloads) {
      val result = try {
        val tmpChapterDir = directoryProvider.getTempChapterDir(download)
        downloadChapter(download, tmpChapterDir)
        Result.Success(download, tmpChapterDir)
      } catch (e: Throwable) {
        Result.Failure(download, e)
      }

      downloadResult.send(result)
    }
  }

  @Suppress("BlockingMethodInNonBlockingContext")
  private suspend fun downloadChapter(download: QueuedDownload, tmpChapterDir: Path) {
    val catalog = getLocalCatalog.get(download.sourceId)
    checkNotNull(catalog) { "Catalog not found" }

    val source = catalog.source
    require(source is HttpSource) { "Source must be an HttpSource for downloading" }

    // TODO
    val chapter = ChapterInfo("", "")

    val pages = (download.pages as? MutableList)
      ?: flow { emit(source.getPageList(chapter)) }
        .retry(1) { delay(retryDelay); true }
        .single()
        .toMutableList()
        .also { download.pages = it }

    if (pages.isEmpty()) {
      throw PageListEmpty()
    }

    // Number of digits
    val digits = pages.size.toString().length

    // List of downloaded files when the download starts
    val downloadedFilesWithoutExtensionOnStart = if (fileSystem.exists(tmpChapterDir)) {
      val allFiles = fileSystem.list(tmpChapterDir).asSequence()
      val tmpFilesFilter: (Path) -> Boolean = { it.name.endsWith(".tmp") }

      // Delete all temporary (unfinished) files or with a wrong number of digits
      allFiles
        .filter { tmpFilesFilter(it) || it.nameWithoutExtension.length != digits }
        .forEach { fileSystem.delete(it) }

      allFiles
        .filterNot(tmpFilesFilter)
        .map { it.nameWithoutExtension }
    } else {
      fileSystem.createDirectories(tmpChapterDir)
      emptySequence<Path>()
    }.toSet()

    var anyError = false

    flowOf(*pages.toTypedArray())
      .withIndex()
      .flatMapConcat { (index, page) ->
        // Check if page was downloaded
        val nameWithoutExtension = getPageName(index + 1, digits)
        if (nameWithoutExtension in downloadedFilesWithoutExtensionOnStart) {
          return@flatMapConcat emptyFlow<Page>()
        }

        val tmpFile = tmpChapterDir / "$nameWithoutExtension.tmp"

        // Retrieve complete page if needed
        val completePageFlow = when (page) {
          is PageUrl -> {
            flow { emit(source.getPage(page)) }
              .retry(1) { delay(retryDelay); true }
              .onEach { pages[index] = it }
              .catch {
                anyError = true
                emptyFlow<PageComplete>()
              }
          }
          is PageComplete -> flowOf(page)
        }

        // Download page
        completePageFlow.flatMapConcat { cpage ->
          flowOf(Unit)
            .onEach {
              when (cpage) {
                is ImageUrl -> {
                  val (client, request) = source.getImageRequest(cpage)
                  val body = client.request<ByteReadChannel>(request)
                  val head = body.peek(32)

                  val finalFile = getImageFile(tmpFile, head)
                  body.saveTo(tmpFile, fileSystem)
                  fileSystem.atomicMove(tmpFile, finalFile)
                }
                is ImageBase64 -> {
                  val dataSource = DataUriStringSource(cpage.data).buffer()
                  val head = dataSource.peek().readByteArray(32)
                  val finalFile = getImageFile(tmpFile, head)
                  dataSource.saveTo(tmpFile, fileSystem)
                  fileSystem.atomicMove(tmpFile, finalFile)
                }
                is Text -> {
                  val finalFile = tmpFile.parent!! / "$nameWithoutExtension.txt"
                  fileSystem.write(tmpFile) { writeUtf8(cpage.text) }
                  fileSystem.atomicMove(tmpFile, finalFile)
                }
              }
            }
            .retry(2) { delay(retryDelay); true }
            .catch {
              anyError = true
              emptyFlow<Unit>()
            }
        }
      }
      .collect()

    if (anyError) {
      throw Exception("Incomplete download")
    }
  }

  private fun getImageFile(tmpFile: Path, head: ByteArray): Path {
    val imageType = ImageUtil.findType(head)
    return if (imageType != null) {
      tmpFile.parent!! / "${tmpFile.nameWithoutExtension}.${imageType.extension}"
    } else {
      tmpFile.parent!! / tmpFile.nameWithoutExtension
    }
  }

  private fun getPageName(pageNumber: Int, digits: Int): String {
    return buildString {
      val pageNumberStr = pageNumber.toString()
      repeat(digits - pageNumberStr.length) {
        append(0)
      }
      append(pageNumberStr)
    }
  }

  sealed class Result {
    abstract val download: QueuedDownload
    val success get() = this is Success

    data class Success(override val download: QueuedDownload, val tmpDir: Path) : Result()
    data class Failure(override val download: QueuedDownload, val error: Throwable) : Result()
  }

}
