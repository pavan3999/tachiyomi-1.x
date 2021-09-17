/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.sync.api

sealed class LoginResult {
  data class Token(val token: String) : LoginResult()
  object InvalidCredentials : LoginResult()
  data class Error(val error: Throwable) : LoginResult()
}
