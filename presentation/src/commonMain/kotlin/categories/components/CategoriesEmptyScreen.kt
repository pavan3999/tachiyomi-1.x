/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.categories.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tachiyomi.i18n.MR
import tachiyomi.ui.categories.CategoriesState
import tachiyomi.ui.core.components.EmptyScreen

@Composable
fun CategoriesEmptyScreen(state: CategoriesState) {
  Box {
    EmptyScreen(MR.strings.information_no_categories)
    CategoriesFloatingActionButton(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(16.dp),
      state = state
    )
  }
}
