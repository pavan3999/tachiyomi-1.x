/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.updates.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import tachiyomi.core.di.Inject
import tachiyomi.domain.updates.model.UpdatesManga
import tachiyomi.domain.updates.service.UpdatesRepository

class GetUpdatesGroupByDate @Inject internal constructor(
  private val repository: UpdatesRepository
) {

  fun subscribeAll(): Flow<Map<LocalDate, List<UpdatesManga>>> {
    return repository.subscribeAll()
      .mapLatest { updates ->
        updates.groupBy { it.date.toLocalDate() }
      }
  }

}
