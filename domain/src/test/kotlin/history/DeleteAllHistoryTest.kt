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
import tachiyomi.domain.history.interactor.DeleteAllHistory
import tachiyomi.domain.history.model.History
import tachiyomi.domain.history.service.HistoryRepository

class DeleteAllHistoryTest : StringSpec({
  val repository = mockk<HistoryRepository>()
  val interactor = DeleteAllHistory(repository)
  afterTest { clearAllMocks() }

  "called functions" {
    coEvery { repository.deleteAll() } returns Unit

    interactor.await()

    coVerify { repository.deleteAll() }
  }
  "throws should return internal error" {
    val exception = Exception("A database error")
    coEvery { repository.deleteAll() }.throws(exception)
    assertEquals(DeleteAllHistory.Result.InternalError(exception), interactor.await())
  }
  "deletes all history" {
    coEvery { repository.deleteAll() } returns Unit

    assertEquals(DeleteAllHistory.Result.Success, interactor.await())
  }
})
