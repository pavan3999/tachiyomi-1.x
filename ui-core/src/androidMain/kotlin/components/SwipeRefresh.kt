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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

actual typealias SwipeRefreshState = com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
actual fun rememberSwipeRefreshState(isRefreshing: Boolean): SwipeRefreshState {
  return com.google.accompanist.swiperefresh.rememberSwipeRefreshState(isRefreshing)
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
  com.google.accompanist.swiperefresh.SwipeRefresh(
    state = state,
    onRefresh = onRefresh,
    modifier = modifier,
    swipeEnabled = swipeEnabled,
    refreshTriggerDistance = refreshTriggerDistance,
    indicatorAlignment = indicatorAlignment,
    indicatorPadding = indicatorPadding,
    indicator = indicator,
    clipIndicatorToPadding = clipIndicatorToPadding,
    content = content
  )
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
  com.google.accompanist.swiperefresh.SwipeRefreshIndicator(
    state = state,
    refreshTriggerDistance = refreshTriggerDistance,
    modifier = modifier,
    fade = fade,
    scale = scale,
    arrowEnabled = arrowEnabled,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    shape = shape,
    refreshingOffset = refreshingOffset,
    largeIndication = largeIndication,
    elevation = elevation
  )
}
