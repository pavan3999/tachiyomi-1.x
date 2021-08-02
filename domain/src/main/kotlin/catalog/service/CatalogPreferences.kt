/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.catalog.service

import tachiyomi.core.prefs.Preference
import tachiyomi.core.prefs.PreferenceStore

class CatalogPreferences(private val store: PreferenceStore) {

  fun gridMode(): Preference<Boolean> {
    return store.getBoolean("grid_mode", true)
  }

  fun lastListingUsed(sourceId: Long): Preference<Int> {
    return store.getInt("last_listing_$sourceId", 0)
  }

  fun lastRemoteCheck(): Preference<Long> {
    return store.getLong("last_remote_check", 0)
  }

  fun pinnedCatalogs(): Preference<Set<String>> {
    return store.getStringSet("pinned_catalogs", setOf())
  }

}
