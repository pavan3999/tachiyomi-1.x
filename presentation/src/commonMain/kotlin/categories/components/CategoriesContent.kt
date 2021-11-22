/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.categories.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tachiyomi.domain.library.model.Category
import tachiyomi.ui.categories.CategoriesState

@Composable
fun CategoriesContent(
  state: CategoriesState,
  onMoveUp: (Category) -> Unit,
  onMoveDown: (Category) -> Unit,
) {
  Box {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      itemsIndexed(state.categories) { i, category ->
        CategoryRow(
          state = state,
          category = category,
          moveUpEnabled = i != 0,
          moveDownEnabled = i != state.categories.lastIndex,
          onMoveUp = onMoveUp,
          onMoveDown = onMoveDown
        )
      }
    }
    CategoriesFloatingActionButton(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(16.dp),
      state = state
    )
  }
}