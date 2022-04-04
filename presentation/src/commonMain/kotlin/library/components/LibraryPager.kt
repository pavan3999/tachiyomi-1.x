/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import tachiyomi.domain.library.model.CategoryWithCount
import tachiyomi.domain.library.model.DisplayMode
import tachiyomi.domain.library.model.DisplayMode.Companion.displayMode
import tachiyomi.domain.library.model.LibraryManga
import tachiyomi.ui.core.components.HorizontalPager
import tachiyomi.ui.core.components.PagerState
import tachiyomi.ui.core.providers.LocalWindow

@Composable
fun LibraryPager(
  state: PagerState,
  pageCount: Int,
  categories: List<CategoryWithCount>,
  selectedManga: List<Long>,
  getColumnsForOrientation: CoroutineScope.(Boolean) -> StateFlow<Int>,
  getLibraryForPage: @Composable (Int) -> State<List<LibraryManga>>,
  onClickManga: (LibraryManga) -> Unit,
  onLongClickManga: (LibraryManga) -> Unit
) {
  HorizontalPager(count = pageCount, state = state) { page ->
    val library by getLibraryForPage(page)
    val displayMode = categories[page].category.displayMode
    val columns by if (displayMode != DisplayMode.List) {
      val window = LocalWindow.current
      val isLandscape = window.screenWidthDp > window.screenHeightDp

      with(rememberCoroutineScope()) {
        remember(isLandscape) { getColumnsForOrientation(isLandscape) }.collectAsState()
      }
    } else {
      remember { mutableStateOf(0) }
    }
    when (displayMode) {
      DisplayMode.CompactGrid -> LibraryMangaCompactGrid(
        library = library,
        selectedManga = selectedManga,
        columns = columns,
        onClickManga = onClickManga,
        onLongClickManga = onLongClickManga
      )
      DisplayMode.ComfortableGrid -> LibraryMangaComfortableGrid(
        library = library,
        selectedManga = selectedManga,
        columns = columns,
        onClickManga = onClickManga,
        onLongClickManga = onLongClickManga
      )
      DisplayMode.List -> LibraryMangaList(
        library = library,
        selectedManga = selectedManga,
        onClickManga = onClickManga,
        onLongClickManga = onLongClickManga
      )
    }
  }
}
