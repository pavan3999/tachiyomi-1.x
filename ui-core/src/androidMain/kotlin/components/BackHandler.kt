/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

@file:JvmName("BackHandlerPlatform")

package tachiyomi.ui.core.components

import androidx.compose.runtime.Composable

@Composable
internal actual fun PlatformBackHandler(enabled: Boolean, onBack: () -> Unit) {
  androidx.activity.compose.BackHandler(enabled, onBack)
}
