/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.history.interactor

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import tachiyomi.domain.history.model.History
import tachiyomi.domain.history.service.HistoryRepository
import javax.inject.Inject

class DeleteHistory @Inject constructor(
  private val historyRepository: HistoryRepository
) {

  suspend fun await(history: History) = withContext(NonCancellable) {
    val deletedRows = try {
      historyRepository.delete(history)
    } catch (e: Throwable) {
      return@withContext Result.InternalError(e)
    }

    if (deletedRows == 0) {
      Result.NothingDeleted
    } else {
      Result.Success
    }
  }

  sealed class Result {
    object Success : Result()
    object NothingDeleted : Result()
    data class InternalError(val error: Throwable) : Result()
  }
}