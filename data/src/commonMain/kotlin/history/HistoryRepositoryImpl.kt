/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data.history

import kotlinx.coroutines.flow.Flow
import tachiyomi.core.di.Inject
import tachiyomi.data.Database
import tachiyomi.data.DatabaseHandler
import tachiyomi.domain.history.model.History
import tachiyomi.domain.history.model.HistoryWithRelations
import tachiyomi.domain.history.service.HistoryRepository

internal class HistoryRepositoryImpl @Inject constructor(
  private val handler: DatabaseHandler
) : HistoryRepository {

  override suspend fun find(mangaId: Long, chapterId: Long): History? {
    return handler.awaitOneOrNull { historyQueries.find(mangaId, chapterId, historyMapper) }
  }

  override suspend fun findAll(): List<History> {
    return handler.awaitList { historyQueries.findAll(historyMapper) }
  }

  override fun subscribeAll(): Flow<List<HistoryWithRelations>> {
    return handler
      .subscribeToList { historyQueries.findAllWithRelations(historyWithRelationsMapper) }
  }

  override suspend fun insert(history: History) {
    handler.await { insertBlocking(history) }
  }

  override suspend fun insert(history: List<History>) {
    handler.await(inTransaction = true) {
      for (h in history) {
        insertBlocking(h)
      }
    }
  }

  override suspend fun update(history: History) {
    handler.await { updateBlocking(history) }
  }

  override suspend fun delete(history: History) {
    handler.await { historyQueries.deleteById(history.mangaId, history.chapterId) }
  }

  override suspend fun delete(history: List<History>) {
    handler.await(inTransaction = true) {
      for (h in history) {
        historyQueries.deleteById(h.mangaId, h.chapterId)
      }
    }
  }

  override suspend fun deleteAll() {
    handler.await { historyQueries.deleteAll() }
  }

  private fun Database.insertBlocking(history: History) {
    historyQueries.insert(
      mangaId = history.mangaId,
      chapterId = history.chapterId,
      readAt = history.readAt
    )
  }

  private fun Database.updateBlocking(history: History) {
    historyQueries.update(
      readAt = history.readAt,
      mangaId = history.mangaId,
      chapterId = history.chapterId
    )
  }

}
