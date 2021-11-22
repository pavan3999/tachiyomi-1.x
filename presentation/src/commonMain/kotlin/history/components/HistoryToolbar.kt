/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.history.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DeleteSweep
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
import tachiyomi.ui.history.HistoryState

@Composable
fun HistoryToolbar(
  state: HistoryState,
  onClickDeleteAll: () -> Unit
) {
  when {
    state.isSearching -> {
      HistorySearchToolbar(
        state = state,
        onClickDeleteAll = onClickDeleteAll
      )
    }
    else -> {
      HistoryRegularToolbar(
        state = state,
        onClickDeleteAll = onClickDeleteAll
      )
    }
  }
}

@Composable
fun HistorySearchToolbar(
  state: HistoryState,
  onClickDeleteAll: () -> Unit
) {
  val focusRequester = remember { FocusRequester() }
  val focusManager = LocalFocusManager.current

  Toolbar(
    title = {
      SearchField(
        modifier = Modifier.focusRequester(focusRequester),
        query = state.query!!,
        onChangeQuery = { state.query = it },
        onDone = { focusManager.clearFocus() }
      )
    },
    navigationIcon = {
      IconButton(onClick = { state.query = null }) {
        Icon(Icons.Default.ArrowBack, contentDescription = null)
      }
    },
    actions = {
      IconButton(onClick = { state.query = "" }) {
        Icon(Icons.Default.Close, contentDescription = null)
      }
      IconButton(onClick = {
        onClickDeleteAll()
      }) {
        Icon(
          Icons.Outlined.DeleteSweep,
          contentDescription = localize(MR.strings.clear_history)
        )
      }
    }
  )
  LaunchedEffect(focusRequester) {
    focusRequester.requestFocus()
  }
  BackHandler(onBack = { state.query = null })
}

@Composable
fun HistoryRegularToolbar(
  state: HistoryState,
  onClickDeleteAll: () -> Unit
) {

  Toolbar(
    title = { Text(localize(MR.strings.history_label)) },
    actions = {
      IconButton(onClick = { state.query = "" }) {
        Icon(Icons.Default.Search, contentDescription = null)
      }
      IconButton(onClick = {
        onClickDeleteAll()
      }) {
        Icon(
          Icons.Outlined.DeleteSweep,
          contentDescription = localize(MR.strings.clear_history)
        )
      }
    }
  )
}
