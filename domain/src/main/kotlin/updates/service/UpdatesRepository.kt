/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.updates.service

import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.updates.model.UpdatesManga
import java.util.Date

interface UpdatesRepository {

  fun subscribeAll(): Flow<Map<Date, List<UpdatesManga>>>

}