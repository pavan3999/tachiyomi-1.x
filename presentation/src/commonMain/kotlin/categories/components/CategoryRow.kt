/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.categories.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Label
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tachiyomi.domain.library.model.Category
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.categories.CategoriesState
import tachiyomi.ui.categories.CategoriesViewModel.Dialog

@Composable
fun CategoryRow(
  state: CategoriesState,
  category: Category,
  moveUpEnabled: Boolean = true,
  moveDownEnabled: Boolean = true,
  onMoveUp: (Category) -> Unit = {},
  onMoveDown: (Category) -> Unit = {},
) {
  Card(Modifier.padding(8.dp)) {
    Column {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Outlined.Label,
          modifier = Modifier.padding(16.dp),
          tint = MaterialTheme.colors.primary,
          contentDescription = null,
        )
        Text(
          text = category.name,
          modifier = Modifier
            .weight(1f)
            .padding(end = 16.dp)
        )
      }
      Row(verticalAlignment = Alignment.CenterVertically) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
          val enabledColor = LocalContentColor.current
          val disabledColor = enabledColor.copy(ContentAlpha.disabled)
          IconButton(
            onClick = { onMoveUp(category) },
            enabled = moveUpEnabled
          ) {
            Icon(
              imageVector = Icons.Default.ArrowDropUp,
              tint = if (moveUpEnabled) enabledColor else disabledColor,
              contentDescription = null
            )
          }
          IconButton(
            onClick = { onMoveDown(category) },
            enabled = moveDownEnabled
          ) {
            Icon(
              imageVector = Icons.Default.ArrowDropDown,
              tint = if (moveDownEnabled) enabledColor else disabledColor,
              contentDescription = null
            )
          }
          Spacer(modifier = Modifier.weight(1f))
          IconButton(onClick = { state.dialog = Dialog.Rename(category) }) {
            Icon(
              imageVector = Icons.Outlined.Edit,
              contentDescription = localize(MR.strings.action_edit),
            )
          }
          IconButton(onClick = { state.dialog = Dialog.Delete(category) }) {
            Icon(
              imageVector = Icons.Outlined.Delete,
              contentDescription = localize(MR.strings.action_delete),
            )
          }
        }
      }
    }
  }
}
