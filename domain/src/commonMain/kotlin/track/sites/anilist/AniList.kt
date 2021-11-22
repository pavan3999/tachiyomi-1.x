/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.track.sites.anilist

import io.ktor.http.URLBuilder
import io.ktor.http.Url
import tachiyomi.core.di.Inject
import tachiyomi.core.http.HttpClients
import tachiyomi.core.log.Log
import tachiyomi.domain.track.model.TrackSearchResult
import tachiyomi.domain.track.model.TrackState
import tachiyomi.domain.track.model.TrackStateUpdate
import tachiyomi.domain.track.model.TrackStatus
import tachiyomi.domain.track.service.TrackPreferences
import tachiyomi.domain.track.sites.OAuthTrackSite
import tachiyomi.domain.track.sites.TrackServices

class AniList @Inject constructor(
  private val httpClients: HttpClients,
  private val preferences: TrackPreferences
) : OAuthTrackSite() {

  override val id: Int = TrackServices.ANILIST
  override val name: String = "AniList"

  override suspend fun add(mediaId: Long): Long {
    // TODO
    return 0L
  }

  override suspend fun update(entryId: Long, track: TrackStateUpdate) {
    // TODO
  }

  override suspend fun search(query: String): List<TrackSearchResult> {
    // TODO
    return emptyList()
  }

  override suspend fun getState(entryId: Long): TrackState? {
    // TODO
    return null
  }

  override suspend fun getEntryId(mediaId: Long): Long? {
    // TODO
    return 0L
  }

  override val loginUrl: Url =
    URLBuilder("${baseUrl}oauth/authorize").apply {
      parameters.append("client_id", clientId)
      parameters.append("response_type", "token")
    }
      .build()

  /*
  TODO: AniList's redirect uses a hash, which the deep link system doesn't actually handle (it's
   considered a number)
  Can have a separate activity in the androidMain module to get the intent data instead?
  adb shell am start -a android.intent.action.VIEW -d "tachiyomi://deeplink/track-auth/anilist#access_token=1234&token_type=Bearer&expires_in=7890" tachiyomi.app/tachiyomi.app.Activity
   */
  override val deepLinkUriPattern: String =
    "tachiyomi://deeplink/track-auth/anilist#{data}"

  override suspend fun login(token: String?): Boolean {
    Log.debug { "Tracker AniList token: $token" }
    return if (token != null) {
      true
    } else {
      logout()
      false
    }
  }

  override suspend fun logout() {
    // TODO
  }

  override fun getSupportedStatusList(): List<TrackStatus> {
    // TODO
    return emptyList()
  }

}

private const val baseUrl = "https://anilist.co/api/v2/"

// Registered under arkon's AniList account
private const val clientId = "6570"
