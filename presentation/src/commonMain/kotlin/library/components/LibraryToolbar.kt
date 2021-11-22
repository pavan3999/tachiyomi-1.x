/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FlipToBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import tachiyomi.domain.library.model.CategoryWithCount
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.categories.visibleName
import tachiyomi.ui.core.components.BackHandler
import tachiyomi.ui.core.components.SearchField
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.library.LibraryState

@Composable
fun LibraryToolbar(
  state: LibraryState,
  showCategoryTabs: Boolean,
  showCountInCategory: Boolean,
  onClickRefresh: () -> Unit,
  onClickCloseSelection: () -> Unit,
  onClickSelectAll: () -> Unit,
  onClickUnselectAll: () -> Unit
) = when {
  state.searchQuery != null -> LibrarySearchToolbar(
    searchQuery = state.searchQuery!!,
    onChangeSearchQuery = { state.searchQuery = it },
    onClickCloseSearch = { state.searchQuery = null },
    onClickFilter = { state.showSheet = true }
  )
  state.selectionMode -> LibrarySelectionToolbar(
    selectedManga = state.selectedManga,
    onClickCloseSelection = onClickCloseSelection,
    onClickSelectAll = onClickSelectAll,
    onClickUnselectAll = onClickUnselectAll
  )
  else -> LibraryRegularToolbar(
    selectedCategory = state.selectedCategory,
    showCategoryTabs = showCategoryTabs,
    showCountInCategory = showCountInCategory,
    onClickSearch = { state.searchQuery = "" },
    onClickFilter = { state.showSheet = true },
    onClickRefresh = onClickRefresh
  )
}

@Composable
private fun LibraryRegularToolbar(
  selectedCategory: CategoryWithCount?,
  showCategoryTabs: Boolean,
  showCountInCategory: Boolean,
  onClickSearch: () -> Unit,
  onClickFilter: () -> Unit,
  onClickRefresh: () -> Unit
) {
  Toolbar(
    title = {
      val text = when {
        showCategoryTabs -> localize(MR.strings.library_label)
        selectedCategory != null -> selectedCategory.visibleName + if (!showCountInCategory) {
          ""
        } else {
          " (${selectedCategory.mangaCount})"
        }
        else -> ""
      }
      Text(text)
    },
    actions = {
      IconButton(onClick = onClickSearch) {
        Icon(Icons.Default.Search, contentDescription = null)
      }
      IconButton(onClick = onClickFilter) {
        Icon(Icons.Default.FilterList, contentDescription = null)
      }
      IconButton(onClick = onClickRefresh) {
        Icon(Icons.Default.Refresh, contentDescription = null)
      }
    }
  )
}

@Composable
private fun LibrarySelectionToolbar(
  selectedManga: List<Long>,
  onClickCloseSelection: () -> Unit,
  onClickSelectAll: () -> Unit,
  onClickUnselectAll: () -> Unit
) {
  Toolbar(
    title = { Text("${selectedManga.size}") },
    navigationIcon = {
      IconButton(onClick = onClickCloseSelection) {
        Icon(Icons.Default.Close, contentDescription = null)
      }
    },
    actions = {
      IconButton(onClick = onClickSelectAll) {
        Icon(Icons.Default.SelectAll, contentDescription = null)
      }
      IconButton(onClick = onClickUnselectAll) {
        Icon(Icons.Default.FlipToBack, contentDescription = null)
      }
    }
  )
  BackHandler(onBack = onClickCloseSelection)
}

@Composable
private fun LibrarySearchToolbar(
  searchQuery: String,
  onChangeSearchQuery: (String) -> Unit,
  onClickCloseSearch: () -> Unit,
  onClickFilter: () -> Unit
) {
  val focusRequester = remember { FocusRequester() }
  val focusManager = LocalFocusManager.current

  Toolbar(
    title = {
      SearchField(
        modifier = Modifier.focusRequester(focusRequester),
        query = searchQuery,
        onChangeQuery = onChangeSearchQuery,
        onDone = { focusManager.clearFocus() }
      )
    },
    navigationIcon = {
      IconButton(onClick = onClickCloseSearch) {
        Icon(Icons.Default.ArrowBack, contentDescription = null)
      }
    },
    actions = {
      IconButton(onClick = { onChangeSearchQuery("") }) {
        Icon(Icons.Default.Close, contentDescription = null)
      }
      IconButton(onClick = {
        onClickFilter()
        focusManager.clearFocus()
      }) {
        Icon(Icons.Default.FilterList, contentDescription = null)
      }
    }
  )
  LaunchedEffect(focusRequester) {
    focusRequester.requestFocus()
  }
  BackHandler(onBack = onClickCloseSearch)
}
