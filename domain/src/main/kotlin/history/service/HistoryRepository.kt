/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.history.service

import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.history.model.History
import tachiyomi.domain.history.model.HistoryWithRelations
import java.util.Date

interface HistoryRepository {

  suspend fun insert(history: History)

  suspend fun insert(history: List<History>)

  suspend fun delete(history: History): Int

  suspend fun delete(history: List<History>): Int

  suspend fun deleteAll()

  suspend fun update(history: History)

  suspend fun find(mangaId: Long, chapterId: Long): History?

  suspend fun getHistory(): List<History>

  fun getHistoryWithRelationByDate(): Flow<Map<Date, List<HistoryWithRelations>>>

}