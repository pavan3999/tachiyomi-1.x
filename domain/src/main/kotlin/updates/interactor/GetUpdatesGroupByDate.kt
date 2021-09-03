/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.updates.interactor

import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.updates.model.UpdatesManga
import tachiyomi.domain.updates.service.UpdatesRepository
import java.util.Date
import javax.inject.Inject

class GetUpdatesGroupByDate @Inject internal constructor(
  val repository: UpdatesRepository
) {

  fun subscribeAll(): Flow<Map<Date, List<UpdatesManga>>> {
    return repository.subscribeAll()
  }

}