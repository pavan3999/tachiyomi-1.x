/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

@file:JvmName("AlertDialogPlatform")

package tachiyomi.ui.core.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal actual fun PlatformAlertDialog(
  onDismissRequest: () -> Unit,
  buttons: @Composable () -> Unit,
  modifier: Modifier,
  title: @Composable (() -> Unit)?,
  text: @Composable (() -> Unit)?,
  shape: Shape,
  backgroundColor: Color,
  contentColor: Color,
  properties: DialogProperties
) {
  androidx.compose.material.AlertDialog(
    onDismissRequest = onDismissRequest,
    buttons = buttons,
    modifier = modifier,
    title = title,
    text = text,
    shape = shape,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    properties = androidx.compose.ui.window.DialogProperties(
      dismissOnBackPress = properties.dismissOnBackPress,
      dismissOnClickOutside = properties.dismissOnClickOutside,
      usePlatformDefaultWidth = properties.usePlatformDefaultWidth
    )
  )
}
