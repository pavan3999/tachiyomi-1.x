/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.categories.components

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.categories.CategoriesState
import tachiyomi.ui.categories.CategoriesViewModel

@Composable
fun CategoriesFloatingActionButton(
  modifier: Modifier,
  state: CategoriesState
) {
  ExtendedFloatingActionButton(
    text = { Text(localize(MR.strings.action_add)) },
    icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
    modifier = modifier,
    onClick = { state.dialog = CategoriesViewModel.Dialog.Create }
  )
}
