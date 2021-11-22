/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tachiyomi.domain.library.model.CategoryWithCount
import tachiyomi.ui.categories.visibleName
import tachiyomi.ui.core.components.PagerState
import tachiyomi.ui.core.components.pagerTabIndicatorOffset
import tachiyomi.ui.core.theme.AppColors

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LibraryTabs(
  state: PagerState,
  visible: Boolean,
  categories: List<CategoryWithCount>,
  showCount: Boolean,
  onClickTab: (Int) -> Unit
) {
  AnimatedVisibility(
    visible = visible,
    enter = expandVertically(),
    exit = shrinkVertically()
  ) {
    ScrollableTabRow(
      selectedTabIndex = state.currentPage,
      backgroundColor = AppColors.current.bars,
      contentColor = AppColors.current.onBars,
      edgePadding = 0.dp,
      indicator = { TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(state, it)) }
    ) {
      categories.forEachIndexed { i, category ->
        Tab(
          selected = state.currentPage == i,
          onClick = { onClickTab(i) },
          text = {
            Text(
              category.visibleName + if (!showCount) {
                ""
              } else {
                " (${category.mangaCount})"
              }
            )
          }
        )
      }
    }
  }
}
