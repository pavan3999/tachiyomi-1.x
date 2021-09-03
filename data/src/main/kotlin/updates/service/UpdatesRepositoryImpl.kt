/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data.updates.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import tachiyomi.data.AppDatabase
import tachiyomi.domain.updates.model.UpdatesManga
import tachiyomi.domain.updates.service.UpdatesRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class UpdatesRepositoryImpl @Inject constructor(
  appDatabase: AppDatabase
) : UpdatesRepository {

  val db = appDatabase.updates

  override fun subscribeAll(): Flow<Map<Date, List<UpdatesManga>>> {
    val formatter = SimpleDateFormat("yy-MM-dd", Locale.getDefault())
    return db.subscribeAll().mapLatest { updates ->
      updates
        .groupBy { formatter.parse(it.date) }
        .toSortedMap(reverseOrder())
    }
  }
}