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
import tachiyomi.core.di.Inject
import tachiyomi.domain.history.model.History
import tachiyomi.domain.history.service.HistoryRepository

class UpsertHistory @Inject constructor(
  private val historyRepository: HistoryRepository
) {

  suspend fun await(history: History) = withContext(NonCancellable) {
    val oneOrNull = historyRepository.find(history.mangaId, history.chapterId)

    try {
      if (oneOrNull != null) {
        historyRepository.update(history)
      } else {
        historyRepository.insert(history)
      }
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
