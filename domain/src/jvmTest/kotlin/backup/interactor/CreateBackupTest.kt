/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.backup.interactor

import io.kotest.core.spec.style.StringSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coInvoke
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import okio.IOException
import okio.sink
import tachiyomi.core.db.Transactions
import tachiyomi.domain.backup.model.Backup
import tachiyomi.domain.library.model.Category
import tachiyomi.domain.library.service.CategoryRepository
import tachiyomi.domain.manga.model.Chapter
import tachiyomi.domain.manga.model.Manga
import tachiyomi.domain.manga.service.ChapterRepository
import tachiyomi.domain.manga.service.MangaRepository
import tachiyomi.domain.track.model.Track
import tachiyomi.domain.track.service.TrackRepository
import java.io.File

class CreateBackupTest : StringSpec({

  val mangaRepository = mockk<MangaRepository>(relaxed = true)
  val categoryRepository = mockk<CategoryRepository>(relaxed = true)
  val chapterRepository = mockk<ChapterRepository>(relaxed = true)
  val trackRepository = mockk<TrackRepository>(relaxed = true)
  val transactions = mockk<Transactions>(relaxed = true)
  val interactor = CreateBackup(mangaRepository, categoryRepository, chapterRepository,
    trackRepository, transactions)
  afterTest { clearAllMocks() }

  // Set defaults for repositories
  beforeTest {
    coEvery { categoryRepository.findAll() } returns listOf()
    coEvery { categoryRepository.findCategoriesOfManga(any()) } returns listOf()
    coEvery { mangaRepository.findFavorites() } returns listOf()
    coEvery { chapterRepository.findForManga(any()) } returns listOf()
    coEvery { trackRepository.findAllForManga(any()) } returns listOf()
    coEvery { transactions.run(captureCoroutine<suspend () -> Backup>()) } coAnswers {
      coroutine<suspend () -> Backup>().coInvoke()
    }
  }

  "dumps nothing" {
    interactor.createDump()

    coVerify {
      mangaRepository.findFavorites()
      categoryRepository.findAll()
    }
  }
  "dumps library" {
    coEvery { mangaRepository.findFavorites() } returns listOf(
      Manga(id = 1, sourceId = 0, key = "key1", title = "title1"),
      Manga(id = 2, sourceId = 1, key = "key2", title = "title2")
    )

    val result = interactor.dumpLibrary()

    coVerify { mangaRepository.findFavorites() }
    result shouldHaveSize 2
  }
  "dumps chapters" {
    coEvery { chapterRepository.findForManga(any()) } returns listOf(
      Chapter(id = 1, mangaId = 4, key = "chapter1", name = "Chapter 1"),
      Chapter(id = 2, mangaId = 4, key = "chapter2", name = "Chapter 2"),
      Chapter(id = 3, mangaId = 4, key = "chapter3", name = "Chapter 3")
    )

    val result = interactor.dumpChapters(4)

    coVerify { chapterRepository.findForManga(4) }
    result shouldHaveSize 3
  }
  "dumps categories of manga" {
    coEvery { categoryRepository.findCategoriesOfManga(any()) } returns listOf(
      Category(id = Category.ALL_ID, order = 0, updateInterval = 0),
      Category(id = Category.UNCATEGORIZED_ID, order = 1, updateInterval = 0),
      Category(id = 1, name = "cat1", order = 2, updateInterval = 0),
      Category(id = 2, name = "cat2", order = 3, updateInterval = 0)
    )

    val result = interactor.dumpMangaCategories(3)

    coVerify { categoryRepository.findCategoriesOfManga(3) }
    result shouldHaveSize 2
  }
  "dumps categories" {
    coEvery { categoryRepository.findAll() } returns listOf(
      Category(id = Category.ALL_ID, order = 0, updateInterval = 0),
      Category(id = Category.UNCATEGORIZED_ID, order = 1, updateInterval = 0),
      Category(id = 1, name = "cat1", order = 2, updateInterval = 0),
      Category(id = 2, name = "cat2", order = 3, updateInterval = 0)
    )

    val result = interactor.dumpCategories()

    coVerify { categoryRepository.findAll() }
    result shouldHaveSize 2
  }
  "dumps tracks" {
    coEvery { trackRepository.findAllForManga(1) } returns listOf(
      Track(id = 1, mangaId = 1, siteId = 1, entryId = 1),
      Track(id = 1, mangaId = 1, siteId = 1, entryId = 1)
    )

    val result = interactor.dumpTracks(1)

    coVerify { trackRepository.findAllForManga(1) }
    result shouldHaveSize 2
  }

  "writes to disk" {
    val file = tempfile("dump.gz")

    val result = interactor.saveTo(file)
    result.shouldBeInstanceOf<CreateBackup.Result.Success>()
    file.length() shouldBeGreaterThan 0
  }

  "fails to write to disk" {
    val file = tempfile("dumpfail.gz")
    mockkStatic("okio.Okio")
    val error = IOException("Simulated IO exception")
    every { file.sink(any()) } throws error

    val result = interactor.saveTo(file)
    result.shouldBeInstanceOf<CreateBackup.Result.Error>()
    result.error shouldBe error
  }

  "creates a full backup" {
    val file = File("/tmp/dump1.gz")

    coEvery { categoryRepository.findAll() } returns listOf(
      Category(id = Category.ALL_ID, order = 0, updateInterval = 0),
      Category(id = Category.UNCATEGORIZED_ID, order = 1, updateInterval = 0),
      Category(id = 1, name = "cat1", order = 2, updateInterval = 0),
      Category(id = 2, name = "cat2", order = 3, updateInterval = 0)
    )

    coEvery { mangaRepository.findFavorites() } returns listOf(
      Manga(id = 1, sourceId = 1, key = "key1", title = "title1"),
      Manga(id = 2, sourceId = 2, key = "key2", title = "title2"),
      Manga(id = 3, sourceId = 3, key = "key3", title = "title3")
    )

    coEvery { categoryRepository.findCategoriesOfManga(1) } returns listOf(
      Category(id = 1, name = "cat1", order = 2, updateInterval = 0),
    )
    coEvery { categoryRepository.findCategoriesOfManga(2) } returns listOf(
      Category(id = 1, name = "cat1", order = 2, updateInterval = 0),
      Category(id = 2, name = "cat2", order = 3, updateInterval = 0)
    )
    coEvery { categoryRepository.findCategoriesOfManga(3) } returns listOf(
      Category(id = 2, name = "cat2", order = 3, updateInterval = 0)
    )

    coEvery { chapterRepository.findForManga(1) } returns listOf(
      Chapter(id = 1, mangaId = 1, key = "chapter1", name = "Chapter 1"),
      Chapter(id = 2, mangaId = 1, key = "chapter2", name = "Chapter 2"),
      Chapter(id = 3, mangaId = 1, key = "chapter3", name = "Chapter 3")
    )
    coEvery { chapterRepository.findForManga(2) } returns listOf(
      Chapter(id = 4, mangaId = 2, key = "chapter1", name = "Chapter 1"),
      Chapter(id = 5, mangaId = 2, key = "chapter2", name = "Chapter 2"),
      Chapter(id = 6, mangaId = 2, key = "chapter3", name = "Chapter 3")
    )
    coEvery { chapterRepository.findForManga(3) } returns listOf(
      Chapter(id = 7, mangaId = 3, key = "chapter1", name = "Chapter 1"),
      Chapter(id = 8, mangaId = 3, key = "chapter2", name = "Chapter 2"),
      Chapter(id = 9, mangaId = 3, key = "chapter3", name = "Chapter 3")
    )

    coEvery { trackRepository.findAllForManga(1) } returns listOf(
      Track(id = 1, mangaId = 1, siteId = 1, entryId = 1),
    )
    coEvery { trackRepository.findAllForManga(2) } returns listOf(
      Track(id = 1, mangaId = 2, siteId = 1, entryId = 1),
      Track(id = 2, mangaId = 2, siteId = 2, entryId = 2),
    )
    coEvery { trackRepository.findAllForManga(3) } returns listOf(
      Track(id = 3, mangaId = 3, siteId = 3, entryId = 1)
    )

    val result = interactor.saveTo(file)
    result.shouldBeInstanceOf<CreateBackup.Result.Success>()
  }

})
