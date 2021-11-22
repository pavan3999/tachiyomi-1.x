/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.modifiers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

actual fun Modifier.statusBarsPadding(): Modifier = Modifier

@Composable
actual fun rememberNavigationBarsInsetsPaddingValues(
  additionalStart: Dp,
  additionalTop: Dp,
  additionalEnd: Dp,
  additionalBottom: Dp
) = remember {
  PaddingValues(
    start = additionalStart,
    top = additionalTop,
    end = additionalEnd,
    bottom = additionalBottom
  )
}
