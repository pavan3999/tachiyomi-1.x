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

interface HistoryRepository {

  suspend fun find(mangaId: Long, chapterId: Long): History?

  suspend fun findAll(): List<History>
  
  fun subscribeAll(): Flow<List<HistoryWithRelations>>

  suspend fun insert(history: History)

  suspend fun insert(history: List<History>)

  suspend fun update(history: History)

  suspend fun delete(history: History)

  suspend fun delete(history: List<History>)

  suspend fun deleteAll()

}
