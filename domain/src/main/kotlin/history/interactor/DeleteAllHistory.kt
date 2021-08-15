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
import tachiyomi.domain.history.service.HistoryRepository
import javax.inject.Inject

class DeleteAllHistory @Inject constructor(
  private val historyRepository: HistoryRepository
) {

  suspend fun await() = withContext(NonCancellable) {
    try {
      historyRepository.deleteAll()
    } catch (e: Throwable) {
      return@withContext Result.InternalError(e)
    }

    Result.Success
  }

  sealed class Result {
    object Success : Result()
    data class InternalError(val error: Throwable) : Result()
  }
}