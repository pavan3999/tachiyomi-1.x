/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tachiyomi.domain.history.model.HistoryWithRelations
import tachiyomi.ui.core.components.RelativeTimeText
import tachiyomi.ui.core.modifiers.rememberNavigationBarsInsetsPaddingValues
import tachiyomi.ui.history.HistoryState

@Composable
fun HistoryContent(
  state: HistoryState,
  onClickItem: (HistoryWithRelations) -> Unit,
  onClickDelete: (HistoryWithRelations) -> Unit,
  onClickPlay: (HistoryWithRelations) -> Unit
) {
  LazyColumn(
    contentPadding = rememberNavigationBarsInsetsPaddingValues(
      additionalBottom = 16.dp,
      additionalTop = 8.dp
    )
  ) {
    state.history.forEach { (date, list) ->
      stickyHeader {
        RelativeTimeText(
          date = date,
          modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        )
      }

      items(list) { item ->
        HistoryItem(
          history = item,
          onClickItem = onClickItem,
          onClickDelete = onClickDelete,
          onClickPlay = onClickPlay
        )
      }
    }
  }
}
