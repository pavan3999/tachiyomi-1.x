/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// TODO(inorichi): this should be removed when/if accompanist moves to multiplatform
expect class SwipeRefreshState {
  var isRefreshing: Boolean
}

@Composable
expect fun rememberSwipeRefreshState(isRefreshing: Boolean): SwipeRefreshState

@Composable
internal expect fun PlatformSwipeRefresh(
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
)

@Composable
internal expect fun PlatformSwipeRefreshIndicator(
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
)

@Composable
fun SwipeRefresh(
  state: SwipeRefreshState,
  onRefresh: () -> Unit,
  modifier: Modifier = Modifier,
  swipeEnabled: Boolean = true,
  refreshTriggerDistance: Dp = 80.dp,
  indicatorAlignment: Alignment = Alignment.TopCenter,
  indicatorPadding: PaddingValues = PaddingValues(0.dp),
  indicator: @Composable (state: SwipeRefreshState, refreshTrigger: Dp) -> Unit = { s, trigger ->
    SwipeRefreshIndicator(s, trigger)
  },
  clipIndicatorToPadding: Boolean = true,
  content: @Composable () -> Unit,
) {
  PlatformSwipeRefresh(
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
fun SwipeRefreshIndicator(
  state: SwipeRefreshState,
  refreshTriggerDistance: Dp,
  modifier: Modifier = Modifier,
  fade: Boolean = true,
  scale: Boolean = false,
  arrowEnabled: Boolean = true,
  backgroundColor: Color = MaterialTheme.colors.surface,
  contentColor: Color = contentColorFor(backgroundColor),
  shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
  refreshingOffset: Dp = 16.dp,
  largeIndication: Boolean = false,
  elevation: Dp = 6.dp,
) {
  PlatformSwipeRefreshIndicator(
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
