/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
expect class PagerState {
  val pageCount: Int
  var currentPage: Int
  val targetPage: Int
  val currentPageOffset: Float

  suspend fun animateScrollToPage(
    page: Int,
    pageOffset: Float = 0f,
  )
}

@Stable
expect interface PagerScope {
  val currentPage: Int
  val currentPageOffset: Float
}

@Composable
fun rememberPagerState(
  initialPage: Int = 0
) = platformRememberPagerState(initialPage)

@Composable
internal expect fun platformRememberPagerState(
  initialPage: Int
): PagerState

@Composable
fun HorizontalPager(
  count: Int,
  modifier: Modifier = Modifier,
  state: PagerState = rememberPagerState(),
  reverseLayout: Boolean = false,
  itemSpacing: Dp = 0.dp,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
  key: ((page: Int) -> Any)? = null,
  content: @Composable PagerScope.(page: Int) -> Unit,
) {
  PlatformHorizontalPager(
    count = count,
    modifier = modifier,
    state = state,
    reverseLayout = reverseLayout,
    itemSpacing = itemSpacing,
    contentPadding = contentPadding,
    verticalAlignment = verticalAlignment,
    key = key,
    content = content
  )
}

@Composable
internal expect fun PlatformHorizontalPager(
  count: Int,
  modifier: Modifier,
  state: PagerState,
  reverseLayout: Boolean,
  itemSpacing: Dp,
  contentPadding: PaddingValues,
  verticalAlignment: Alignment.Vertical,
  key: ((page: Int) -> Any)?,
  content: @Composable PagerScope.(page: Int) -> Unit,
)

expect fun Modifier.pagerTabIndicatorOffset(
  pagerState: PagerState,
  tabPositions: List<TabPosition>,
): Modifier
