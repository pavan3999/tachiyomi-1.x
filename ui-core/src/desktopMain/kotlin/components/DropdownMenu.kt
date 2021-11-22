/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

@file:JvmName("DropdownMenuPlatform")

package tachiyomi.ui.core.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset

@Composable
internal actual fun PlatformDropdownMenu(
  expanded: Boolean,
  onDismissRequest: () -> Unit,
  modifier: Modifier,
  offset: DpOffset,
  properties: PopupProperties,
  content: @Composable ColumnScope.() -> Unit
) {
  androidx.compose.material.DropdownMenu(
    expanded = expanded,
    onDismissRequest = onDismissRequest,
    focusable = properties.focusable,
    modifier = modifier,
    offset = offset,
    content = content
  )
}

@Composable
internal actual fun PlatformDropdownMenuItem(
  onClick: () -> Unit,
  modifier: Modifier,
  enabled: Boolean,
  contentPadding: PaddingValues,
  interactionSource: MutableInteractionSource,
  content: @Composable RowScope.() -> Unit
) {
  androidx.compose.material.DropdownMenuItem(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    content = content
  )
}
