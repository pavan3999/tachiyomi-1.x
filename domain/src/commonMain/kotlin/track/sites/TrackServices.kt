/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.track.sites

import tachiyomi.core.di.Inject
import tachiyomi.core.http.HttpClients
import tachiyomi.domain.track.service.TrackPreferences

class TrackServices @Inject constructor(
  httpClients: HttpClients,
  trackPreferences: TrackPreferences
) {

  val trackers = listOf<TrackSite>(
//    MyAnimeList(httpClients, trackPreferences),
  )

  fun get(id: Int): TrackSite? {
    return trackers.find { it.id == id }
  }

  companion object {
    const val MYANIMELIST = 1
    const val ANILIST = 2
    const val KITSU = 3
    const val SHIKIMORI = 4
    const val BANGUMI = 5
  }

}
