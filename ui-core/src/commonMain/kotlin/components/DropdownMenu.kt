/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Immutable
class PopupProperties(
  val focusable: Boolean = false,
  val dismissOnBackPress: Boolean = true,
  val dismissOnClickOutside: Boolean = true,
  val excludeFromSystemGesture: Boolean = true,
  val clippingEnabled: Boolean = true,
  val usePlatformDefaultWidth: Boolean = false
)

@Composable
internal expect fun PlatformDropdownMenu(
  expanded: Boolean,
  onDismissRequest: () -> Unit,
  modifier: Modifier,
  offset: DpOffset,
  properties: PopupProperties,
  content: @Composable ColumnScope.() -> Unit
)

@Composable
internal expect fun PlatformDropdownMenuItem(
  onClick: () -> Unit,
  modifier: Modifier,
  enabled: Boolean,
  contentPadding: PaddingValues,
  interactionSource: MutableInteractionSource,
  content: @Composable RowScope.() -> Unit
)

@Composable
fun DropdownMenu(
  expanded: Boolean,
  onDismissRequest: () -> Unit,
  modifier: Modifier = Modifier,
  offset: DpOffset = DpOffset(0.dp, 0.dp),
  properties: PopupProperties = PopupProperties(),
  content: @Composable ColumnScope.() -> Unit
) {
  PlatformDropdownMenu(
    expanded = expanded,
    onDismissRequest = onDismissRequest,
    modifier = modifier,
    offset = offset,
    properties = properties,
    content = content
  )
}

@Composable
fun DropdownMenuItem(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.() -> Unit
) {
  PlatformDropdownMenuItem(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    content = content
  )
}
