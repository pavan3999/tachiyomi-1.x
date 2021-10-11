/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.catalog.interactor

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import tachiyomi.core.di.Inject
import tachiyomi.core.log.Log
import tachiyomi.domain.catalog.service.CatalogPreferences
import tachiyomi.domain.catalog.service.CatalogRemoteApi
import tachiyomi.domain.catalog.service.CatalogRemoteRepository
import kotlin.time.Duration

class SyncRemoteCatalogs @Inject internal constructor(
  private val catalogRemoteRepository: CatalogRemoteRepository,
  private val catalogRemoteApi: CatalogRemoteApi,
  private val catalogPreferences: CatalogPreferences
) {

  suspend fun await(forceRefresh: Boolean): Boolean {
    val lastCheckPref = catalogPreferences.lastRemoteCheck()
    val lastCheck = Instant.fromEpochMilliseconds(lastCheckPref.get())
    val now = Clock.System.now()

    if (forceRefresh || now - lastCheck > minTimeApiCheck) {
      try {
        val newCatalogs = catalogRemoteApi.fetchCatalogs()
        catalogRemoteRepository.setRemoteCatalogs(newCatalogs)
        lastCheckPref.set(Clock.System.now().toEpochMilliseconds())
        return true
      } catch (e: Exception) {
        Log.warn(e, "Failed to fetch remote catalogs")
      }
    }

    return false
  }

  internal companion object {
    val minTimeApiCheck = Duration.minutes(5)
  }

}
