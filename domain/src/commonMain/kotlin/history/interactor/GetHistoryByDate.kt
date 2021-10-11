/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.history.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import tachiyomi.core.di.Inject
import tachiyomi.domain.history.model.HistoryWithRelations
import tachiyomi.domain.history.service.HistoryRepository

class GetHistoryByDate @Inject constructor(
  private val historyRepository: HistoryRepository
) {

  fun subscribeAll(): Flow<Map<LocalDate, List<HistoryWithRelations>>> {
    return historyRepository.subscribeAll()
      .mapLatest { history ->
        history.groupBy { it.date.toLocalDate() }
      }
  }

}
