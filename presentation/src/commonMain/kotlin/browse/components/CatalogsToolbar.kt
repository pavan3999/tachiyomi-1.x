/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.browse.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.BackHandler
import tachiyomi.ui.core.components.SearchField
import tachiyomi.ui.core.components.Toolbar

@Composable
fun CatalogsToolbar(
  searchQuery: String?,
  onClickCloseSearch: () -> Unit,
  onChangeSearchQuery: (String) -> Unit
) {
  if (searchQuery == null) {
    CatalogsRegularToolbar(
      onClickSearch = { onChangeSearchQuery("") }
    )
  } else {
    CatalogsSearchToolbar(
      searchQuery = searchQuery,
      onChangeSearchQuery = onChangeSearchQuery,
      onClickCloseSearch = onClickCloseSearch
    )
  }
}

@Composable
private fun CatalogsRegularToolbar(
  onClickSearch: () -> Unit
) {
  Toolbar(
    title = { Text(localize(MR.strings.browse_label)) },
    actions = {
      IconButton(onClick = onClickSearch) {
        Icon(Icons.Default.Search, contentDescription = null)
      }
      IconButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Default.TravelExplore, contentDescription = null)
      }
      IconButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Default.MoreVert, contentDescription = null)
      }
    }
  )
}

@Composable
private fun CatalogsSearchToolbar(
  searchQuery: String,
  onChangeSearchQuery: (String) -> Unit,
  onClickCloseSearch: () -> Unit
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
    }
  )
  LaunchedEffect(focusRequester) {
    focusRequester.requestFocus()
  }
  BackHandler(onBack = onClickCloseSearch)
}
