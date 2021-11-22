/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction

@Composable
fun SearchField(
  modifier: Modifier = Modifier,
  query: String,
  onChangeQuery: (String) -> Unit,
  onDone: KeyboardActionScope.() -> Unit
) {
  BasicTextField(
    query,
    onChangeQuery,
    modifier = modifier,
    textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
    cursorBrush = SolidColor(LocalContentColor.current),
    singleLine = true,
    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
    keyboardActions = KeyboardActions(onDone = onDone)
  )
}
