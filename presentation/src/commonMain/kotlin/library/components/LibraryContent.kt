/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tachiyomi.domain.library.model.CategoryWithCount
import tachiyomi.domain.library.model.LibraryManga
import tachiyomi.ui.core.components.rememberPagerState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LibraryContent(
  categories: List<CategoryWithCount>,
  currentPage: Int,
  showPageTabs: Boolean,
  showCountInCategory: Boolean,
  selectedManga: List<Long>,
  getColumnsForOrientation: CoroutineScope.(Boolean) -> StateFlow<Int>,
  getLibraryForPage: @Composable (Int) -> State<List<LibraryManga>>,
  onClickManga: (LibraryManga) -> Unit,
  onLongClickManga: (LibraryManga) -> Unit,
  onPageChanged: (Int) -> Unit
) {
  if (categories.isEmpty()) return

  val scope = rememberCoroutineScope()
//  val infiniteLoop = categories.size > 2 // TODO not supoorted anymore
  val state = rememberPagerState(currentPage)

  LaunchedEffect(state) {
    snapshotFlow { state.currentPage }.collect {
      onPageChanged(it)
    }
  }

  LibraryTabs(
    state = state,
    visible = showPageTabs,
    categories = categories,
    showCount = showCountInCategory,
    onClickTab = { scope.launch { state.animateScrollToPage(it) } }
  )
  LibraryPager(
    state = state,
    pageCount = categories.size,
    categories = categories,
    selectedManga = selectedManga,
    getColumnsForOrientation = getColumnsForOrientation,
    getLibraryForPage = getLibraryForPage,
    onClickManga = onClickManga,
    onLongClickManga = onLongClickManga,
  )
}
