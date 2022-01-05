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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

expect fun Modifier.statusBarsPadding(): Modifier

@Composable
expect fun rememberStatusBarInsetsPaddingValues(
  additionalStart: Dp = 0.dp,
  additionalTop: Dp = 0.dp,
  additionalEnd: Dp = 0.dp,
  additionalBottom: Dp = 0.dp,
): PaddingValues

@Composable
expect fun rememberNavigationBarsInsetsPaddingValues(
  additionalStart: Dp = 0.dp,
  additionalTop: Dp = 0.dp,
  additionalEnd: Dp = 0.dp,
  additionalBottom: Dp = 0.dp,
): PaddingValues
