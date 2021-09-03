/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data.history.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.history.model.History
import tachiyomi.domain.history.model.HistoryWithRelations
import tachiyomi.data.manga.db.BaseDao

@Dao
abstract class HistoryDao : BaseDao<History> {

  @Transaction
  @Query("SELECT *, date(ROUND(readAt / 1000), 'unixepoch', 'localtime') date FROM history")
  abstract fun getHistoryWithRelations(): Flow<List<HistoryWithRelations>>

  @Query("SELECT * FROM history")
  abstract suspend fun getHistory(): List<History>

  @Query("SELECT * FROM history WHERE mangaId = :mangaId AND chapterId = :chapterId")
  abstract suspend fun find(mangaId: Long, chapterId: Long): History?

  @Query("DELETE FROM history")
  abstract suspend fun deleteAll()

}