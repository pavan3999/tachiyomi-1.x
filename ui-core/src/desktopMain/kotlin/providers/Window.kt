/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.providers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

actual class Window {
  actual var screenWidthDp by mutableStateOf(0)
  actual var screenHeightDp by mutableStateOf(0)

  fun setSize(width: Dp, height: Dp) {
    screenWidthDp = width.value.toInt()
    screenHeightDp = height.value.toInt()
  }
}

actual val LocalWindow = staticCompositionLocalOf<Window> {
  error("LocalWindow must be provided")
}
