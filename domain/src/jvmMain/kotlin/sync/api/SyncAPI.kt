/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.sync.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import tachiyomi.core.http.Http
import tachiyomi.core.http.awaitBody
import tachiyomi.core.http.awaitResponse
import tachiyomi.domain.sync.service.SyncPreferences
import javax.inject.Inject

@Serializable
private data class Response(val secret: String)

class SyncAPI @Inject constructor(
  http: Http,
  private val store: SyncPreferences,
  private val device: SyncDevice
) {

  private val client = http.defaultClient
  private val jsonMediaType by lazy { "application/json; charset=utf-8".toMediaType() }

  private val addressPref = store.address()
  private val tokenPref = store.token()

  val address get() = addressPref.get()
  val token get() = tokenPref.get()

  suspend fun login(address: String, username: String, password: String): LoginResult {
    val credentials = Credentials.basic(username, password)

    val reqBody = buildJsonObject {
      put("deviceId", device.getId())
      put("deviceName", device.getName())
      put("platform", device.getPlatform())
    }

    val request = Request.Builder()
      .url("$address/api/v3/auth/tokens")
      .post(reqBody.toString().toRequestBody(jsonMediaType))
      .addHeader("Authorization", credentials)
      .build()

    return try {
      val response = client.newCall(request).awaitResponse()
      if (response.code != 200) {
        response.close()
        return LoginResult.InvalidCredentials
      }
      val body = response.awaitBody()
      val responseBody = Json.decodeFromString(Response.serializer(), body)
      LoginResult.Token(responseBody.secret)
    } catch (e: Exception) {
      LoginResult.Error(e)
    }
  }

}
