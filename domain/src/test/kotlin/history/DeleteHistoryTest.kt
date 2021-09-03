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
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import tachiyomi.domain.history.interactor.DeleteHistory
import tachiyomi.domain.history.model.History
import tachiyomi.domain.history.service.HistoryRepository

class DeleteHistoryTest : StringSpec({
  val repository = mockk<HistoryRepository>()
  val interactor = DeleteHistory(repository)
  afterTest { clearAllMocks() }

  "called function" {
    coEvery { repository.delete(history) } returns 1
    interactor.await(history)
    coVerify { repository.delete(history) }
  }
  "throws should return internal error" {
    val exception = Exception("A database error")
    coEvery { repository.delete(history) }.throws(exception)
    assertEquals(DeleteHistory.Result.InternalError(exception), interactor.await(history))
  }
  "deletes nothing" {
    coEvery { repository.delete(history) } returns 0
    assertEquals(DeleteHistory.Result.NothingDeleted, interactor.await(history))
  }
  "deletes history" {
    coEvery { repository.delete(history) } returns 1
    assertEquals(DeleteHistory.Result.Success, interactor.await(history))
  }
})

private val history = History(1627906952000, 1, 1)