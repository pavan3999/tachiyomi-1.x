/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

@file:JvmName("PagerPlatform")

package tachiyomi.ui.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.google.accompanist.pager.pagerTabIndicatorOffset as accompanistPagerTabIndicatorOffset

actual typealias PagerState = com.google.accompanist.pager.PagerState

actual typealias PagerScope = com.google.accompanist.pager.PagerScope

actual fun Modifier.pagerTabIndicatorOffset(
  pagerState: PagerState,
  tabPositions: List<TabPosition>
) = accompanistPagerTabIndicatorOffset(pagerState, tabPositions)

@Composable
internal actual fun platformRememberPagerState(
  initialPage: Int
) = com.google.accompanist.pager.rememberPagerState(initialPage)

@Composable
internal actual fun PlatformHorizontalPager(
  count: Int,
  modifier: Modifier,
  state: PagerState,
  reverseLayout: Boolean,
  itemSpacing: Dp,
  contentPadding: PaddingValues,
  verticalAlignment: Alignment.Vertical,
  key: ((page: Int) -> Any)?,
  content: @Composable PagerScope.(page: Int) -> Unit
) {
  com.google.accompanist.pager.HorizontalPager(
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
