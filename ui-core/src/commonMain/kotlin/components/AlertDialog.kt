/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Immutable
class DialogProperties(
  val dismissOnBackPress: Boolean = true,
  val dismissOnClickOutside: Boolean = true,
  val usePlatformDefaultWidth: Boolean = true
)

@Composable
internal expect fun PlatformAlertDialog(
  onDismissRequest: () -> Unit,
  buttons: @Composable () -> Unit,
  modifier: Modifier,
  title: (@Composable () -> Unit)?,
  text: @Composable (() -> Unit)?,
  shape: Shape,
  backgroundColor: Color,
  contentColor: Color,
  properties: DialogProperties
)

@Composable
fun AlertDialog(
  onDismissRequest: () -> Unit,
  buttons: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  title: (@Composable () -> Unit)? = null,
  text: @Composable (() -> Unit)? = null,
  shape: Shape = MaterialTheme.shapes.medium,
  backgroundColor: Color = MaterialTheme.colors.surface,
  contentColor: Color = contentColorFor(backgroundColor),
  properties: DialogProperties = DialogProperties()
) {
  PlatformAlertDialog(
    onDismissRequest = onDismissRequest,
    buttons = buttons,
    modifier = modifier,
    title = title,
    text = text,
    shape = shape,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    properties = properties
  )
}
