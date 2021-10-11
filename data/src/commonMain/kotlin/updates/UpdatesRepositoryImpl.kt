/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data.updates

import kotlinx.coroutines.flow.Flow
import tachiyomi.core.di.Inject
import tachiyomi.data.DatabaseHandler
import tachiyomi.domain.updates.model.UpdatesManga
import tachiyomi.domain.updates.service.UpdatesRepository

internal class UpdatesRepositoryImpl @Inject constructor(
  private val handler: DatabaseHandler
) : UpdatesRepository {

  override fun subscribeAll(): Flow<List<UpdatesManga>> {
    return handler.subscribeToList { updatesQueries.findAll(updatesMapper) }
  }

}
