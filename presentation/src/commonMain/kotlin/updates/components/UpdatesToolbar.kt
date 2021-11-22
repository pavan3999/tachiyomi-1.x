/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.updates.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlipToBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.runtime.Composable
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.updates.UpdatesState

@Composable
fun UpdatesToolbar(
  state: UpdatesState,
  onClickCancelSelection: () -> Unit,
  onClickSelectAll: () -> Unit,
  onClickFlipSelection: () -> Unit,
  onClickRefresh: () -> Unit
) {
  when {
    state.hasSelection -> {
      UpdatesSelectionToolbar(
        selectionSize = state.selection.size,
        onClickCancelSelection = onClickCancelSelection,
        onClickSelectAll = onClickSelectAll,
        onClickInvertSelection = onClickFlipSelection
      )
    }
    else -> {
      UpdatesRegularToolbar(
        onClickRefresh = onClickRefresh
      )
    }
  }
}

@Composable
private fun UpdatesSelectionToolbar(
  selectionSize: Int,
  onClickCancelSelection: () -> Unit,
  onClickSelectAll: () -> Unit,
  onClickInvertSelection: () -> Unit
) {
  Toolbar(
    title = { Text("$selectionSize") },
    navigationIcon = {
      IconButton(onClick = onClickCancelSelection) {
        Icon(Icons.Default.Close, contentDescription = null)
      }
    },
    actions = {
      IconButton(onClick = onClickSelectAll) {
        Icon(Icons.Default.SelectAll, contentDescription = null)
      }
      IconButton(onClick = onClickInvertSelection) {
        Icon(Icons.Default.FlipToBack, contentDescription = null)
      }
    }
  )
}

@Composable
fun UpdatesRegularToolbar(onClickRefresh: () -> Unit) {
  Toolbar(
    title = { Text(localize(MR.strings.updates_label)) },
    actions = {
      IconButton(onClick = onClickRefresh) {
        Icon(Icons.Default.Refresh, contentDescription = null)
      }
    }
  )
}
