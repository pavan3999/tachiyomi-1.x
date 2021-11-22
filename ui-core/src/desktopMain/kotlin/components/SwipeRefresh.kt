/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

@file:JvmName("SwipeRefreshPlatform")

package tachiyomi.ui.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

actual class SwipeRefreshState(actual var isRefreshing: Boolean)

@Composable
actual fun rememberSwipeRefreshState(isRefreshing: Boolean): SwipeRefreshState {
  return remember { SwipeRefreshState(isRefreshing) }
}

@Composable
internal actual fun PlatformSwipeRefresh(
  state: SwipeRefreshState,
  onRefresh: () -> Unit,
  modifier: Modifier,
  swipeEnabled: Boolean,
  refreshTriggerDistance: Dp,
  indicatorAlignment: Alignment,
  indicatorPadding: PaddingValues,
  indicator: @Composable (state: SwipeRefreshState, refreshTrigger: Dp) -> Unit,
  clipIndicatorToPadding: Boolean,
  content: @Composable () -> Unit
) {
  content()
}

@Composable
internal actual fun PlatformSwipeRefreshIndicator(
  state: SwipeRefreshState,
  refreshTriggerDistance: Dp,
  modifier: Modifier,
  fade: Boolean,
  scale: Boolean,
  arrowEnabled: Boolean,
  backgroundColor: Color,
  contentColor: Color,
  shape: Shape,
  refreshingOffset: Dp,
  largeIndication: Boolean,
  elevation: Dp,
) {

}
