/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.track.sites

import io.ktor.http.Url
import tachiyomi.domain.track.model.TrackSearchResult
import tachiyomi.domain.track.model.TrackState
import tachiyomi.domain.track.model.TrackStateUpdate
import tachiyomi.domain.track.model.TrackStatus

sealed class TrackSite {

  abstract val id: Int

  abstract val name: String

  abstract suspend fun add(mediaId: Long): Long

  abstract suspend fun update(entryId: Long, track: TrackStateUpdate)

  abstract suspend fun search(query: String): List<TrackSearchResult>

  abstract suspend fun getState(entryId: Long): TrackState?

  abstract suspend fun getEntryId(mediaId: Long): Long?

  abstract suspend fun logout()

  abstract fun getSupportedStatusList(): List<TrackStatus>

}

abstract class BasicTrackSite : TrackSite() {

  abstract suspend fun login(username: String, password: String): Boolean

}

abstract class OAuthTrackSite : TrackSite() {

  abstract val loginUrl: Url

  /**
   * Must start with "tachiyomi://track-auth"
   */
  abstract val deepLinkUriPattern: String

  abstract suspend fun login(token: String?): Boolean

}
