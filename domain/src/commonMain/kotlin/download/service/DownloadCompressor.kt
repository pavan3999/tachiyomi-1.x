/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.download.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import tachiyomi.core.di.Inject
import tachiyomi.core.io.createZip
import tachiyomi.core.util.IO
import tachiyomi.domain.download.model.QueuedDownload

internal class DownloadCompressor @Inject constructor(
  private val directoryProvider: DownloadDirectoryProvider,
  private val fileSystem: FileSystem,
) {

  fun worker(
    scope: CoroutineScope,
    download: QueuedDownload,
    tmpChapterDir: Path,
    compressionResult: SendChannel<Result>
  ) = scope.launch(Dispatchers.IO) {
    val chapterDir = directoryProvider.getChapterDir(download)
    val tmpZip = "$chapterDir.cbz.tmp".toPath()
    val result = try {
      compressChapter(tmpChapterDir, tmpZip)
      Result.Success(download, tmpZip)
    } catch (e: Throwable) {
      Result.Failure(download, e)
    }

    compressionResult.send(result)
  }

  private fun compressChapter(tmpChapterDir: Path, tmpZip: Path) {
    val files = fileSystem.list(tmpChapterDir)

    fileSystem.createZip(tmpZip, false) {
      for (path in files) {
        addFile(path.name, fileSystem.source(path))
      }
    }
  }

  sealed class Result {

    abstract val download: QueuedDownload

    val success get() = this is Success

    data class Success(override val download: QueuedDownload, val tmpZip: Path) : Result()
    data class Failure(override val download: QueuedDownload, val error: Throwable) : Result()
  }

}
