/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.categories.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import tachiyomi.domain.library.model.Category
import tachiyomi.ui.R

@Composable
fun CreateCategoryDialog(
  onDismissRequest: () -> Unit,
  onCreate: (String) -> Unit
) {
  val (categoryName, setCategoryName) = remember { mutableStateOf("") }
  val focusRequester = remember { FocusRequester() }
  AlertDialog(
    onDismissRequest = onDismissRequest,
    title = { Text(stringResource(R.string.create_category)) },
    text = {
      OutlinedTextField(
        value = categoryName,
        onValueChange = setCategoryName,
        modifier = Modifier
          .focusRequester(focusRequester)
      )
    },
    buttons = {
      ButtonsRow {
        TextButton(onClick = onDismissRequest) {
          Text(stringResource(R.string.action_cancel))
        }
        TextButton(onClick = {
          onCreate(categoryName)
          onDismissRequest()
        }) {
          Text(stringResource(R.string.action_add))
        }
      }
    }
  )
  LaunchedEffect(Unit) {
    // TODO: find a way to also open keyboard
    focusRequester.requestFocus()
  }
}

@Composable
fun RenameCategoryDialog(
  category: Category,
  onDismissRequest: () -> Unit,
  onRename: (String) -> Unit
) {
  val (textField, setTextField) = remember {
    mutableStateOf(
      TextFieldValue(
        text = category.name, selection =
        TextRange(category.name.length)
      )
    )
  }
  val focusRequester = remember { FocusRequester() }
  AlertDialog(
    onDismissRequest = onDismissRequest,
    title = { Text(stringResource(R.string.rename_category)) },
    text = {
      TextFieldValue()
      OutlinedTextField(
        value = textField,
        onValueChange = setTextField,
        modifier = Modifier
          .focusRequester(focusRequester)
      )
    },
    buttons = {
      ButtonsRow {
        TextButton(onClick = onDismissRequest) {
          Text(stringResource(R.string.action_cancel))
        }
        TextButton(onClick = {
          onRename(textField.text)
          onDismissRequest()
        }) {
          Text(stringResource(R.string.action_edit))
        }
      }
    }
  )
  LaunchedEffect(Unit) {
    // TODO: find a way to also open keyboard
    focusRequester.requestFocus()
  }
}

@Composable
fun DeleteCategoryDialog(
  category: Category,
  onDismissRequest: () -> Unit,
  onDelete: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismissRequest,
    title = { Text(stringResource(R.string.delete_category)) },
    text = {
      Text(stringResource(R.string.delete_category_confirmation, category.name))
    },
    buttons = {
      ButtonsRow {
        TextButton(onClick = {
          onDelete()
          onDismissRequest()
        }) {
          Text(stringResource(R.string.action_yes))
        }
        TextButton(onClick = onDismissRequest) {
          Text(stringResource(R.string.action_no))
        }
      }
    }
  )
}

@Composable
private fun ButtonsRow(buttons: @Composable RowScope.() -> Unit) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp),
    horizontalArrangement = Arrangement.End,
    content = buttons
  )
}
