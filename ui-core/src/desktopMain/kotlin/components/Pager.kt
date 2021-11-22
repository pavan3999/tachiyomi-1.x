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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

actual class PagerState {
  actual val pageCount = 0
  actual var currentPage = 0
  actual val targetPage = 0
  actual val currentPageOffset = 0f

  actual suspend fun animateScrollToPage(
    page: Int,
    pageOffset: Float,
  ) {
  }
}

actual interface PagerScope {
  actual val currentPage: Int
  actual val currentPageOffset: Float
}

actual fun Modifier.pagerTabIndicatorOffset(
  pagerState: PagerState,
  tabPositions: List<TabPosition>
) = this

@Composable
internal actual fun platformRememberPagerState(initialPage: Int): PagerState {
  return remember { PagerState() }
}

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
}
