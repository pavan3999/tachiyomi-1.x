/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.modifiers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding

actual fun Modifier.statusBarsPadding() = statusBarsPadding()

@Composable
actual fun rememberNavigationBarsInsetsPaddingValues(
  additionalStart: Dp,
  additionalTop: Dp,
  additionalEnd: Dp,
  additionalBottom: Dp
) = rememberInsetsPaddingValues(
  insets = LocalWindowInsets.current.navigationBars,
  additionalStart = additionalStart,
  additionalTop = additionalTop,
  additionalEnd = additionalEnd,
  additionalBottom = additionalBottom
)
