/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.download.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.channels.Channel
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import tachiyomi.domain.download.model.QueuedDownload
import java.io.File
import java.util.zip.ZipFile

@Suppress("BlockingMethodInNonBlockingContext")
class DownloadCompressorTest : FunSpec({

  val fileSystem = FileSystem.SYSTEM
  val directoryProvider = mockk<DownloadDirectoryProvider>()
  val compressor = DownloadCompressor(directoryProvider, fileSystem)

  afterTest { clearAllMocks() }

  beforeTest {
    every { directoryProvider.getChapterDir(any()) } returns tempdir("chapterzip").toOkioPath()
  }

  test("compresses a chapter") {
    val download = QueuedDownload(1, 1)
    val tmpChapterDir = tempdir("chapterdir")
    File(tmpChapterDir, "1").createNewFile()
    File(tmpChapterDir, "2").createNewFile()

    val channel = Channel<DownloadCompressor.Result>(Channel.UNLIMITED)
    compressor.worker(this, download, tmpChapterDir.toOkioPath(), channel)
    val result = channel.receive()

    result.success shouldBe true
    result as DownloadCompressor.Result.Success
    val file = result.tmpZip.toFile()
    val zip = ZipFile(file)
    zip.use {
      val entries = it.entries().toList()
      entries shouldHaveSize 2
    }
    FileSystem.SYSTEM.delete(result.tmpZip)
  }

})
