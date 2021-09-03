/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.history

import io.kotest.core.spec.style.StringSpec
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import tachiyomi.domain.history.interactor.UpsertHistory
import tachiyomi.domain.history.model.History
import tachiyomi.domain.history.service.HistoryRepository

class UpsertHistoryTest : StringSpec({
  val repository = mockk<HistoryRepository>()
  val interactor = UpsertHistory(repository)
  afterTest { clearAllMocks() }

  "insert throws should return internal error" {
    coEvery { repository.find(history.mangaId, history.chapterId) } returns null
    val exception = Exception("A database error")
    coEvery { repository.insert(history) }.throws(exception)
    assertEquals(UpsertHistory.Result.InternalError(exception), interactor.await(history))
  }
  "update throws should return internal error" {
    coEvery { repository.find(history.mangaId, history.chapterId) } returns history
    val exception = Exception("A database error")
    coEvery { repository.update(history) }.throws(exception)
    assertEquals(UpsertHistory.Result.InternalError(exception), interactor.await(history))
  }
  "inserts history" {
    coEvery { repository.find(history.mangaId, history.chapterId) } returns null
    coEvery { repository.insert(history) } returns Unit
    assertEquals(UpsertHistory.Result.Success, interactor.await(history))
  }
  "updates history" {
    coEvery { repository.find(history.mangaId, history.chapterId) } returns history
    coEvery { repository.update(history) } returns Unit
    assertEquals(UpsertHistory.Result.Success, interactor.await(history))
  }
})

private val history = History(1627906952000, 1, 1)
