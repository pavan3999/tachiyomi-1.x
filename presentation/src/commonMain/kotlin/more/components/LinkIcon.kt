/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.more.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.StringResource
import tachiyomi.i18n.localize

@Composable
fun LinkIcon(
  modifier: Modifier = Modifier,
  labelRes: StringResource,
  icon: ImageVector,
  url: String,
) {
  val uriHandler = LocalUriHandler.current
  LinkIcon(modifier, labelRes, icon) { uriHandler.openUri(url) }
}

@Composable
fun LinkIcon(
  modifier: Modifier = Modifier,
  labelRes: StringResource,
  icon: ImageVector,
  onClick: () -> Unit,
) {
  Icon(
    modifier = modifier
      .clickable(onClick = onClick)
      .padding(16.dp),
    imageVector = icon,
    contentDescription = localize(labelRes),
  )
}
