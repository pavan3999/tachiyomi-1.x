/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import tachiyomi.ui.core.coil.MangaCover
import tachiyomi.ui.core.coil.rememberImagePainter

@Composable
fun MangaListItem(
  modifier: Modifier = Modifier,
  content: @Composable RowScope.() -> Unit
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    content()
  }
}

@Composable
fun MangaListItemImage(
  modifier: Modifier = Modifier,
  mangaCover: MangaCover
) {
  Image(
    painter = rememberImagePainter(mangaCover),
    contentDescription = null,
    modifier = modifier,
    contentScale = ContentScale.Crop
  )
}

@Composable
fun MangaListItemColumn(
  modifier: Modifier = Modifier,
  content: @Composable ColumnScope.() -> Unit
) {
  Column(
    modifier = modifier
  ) {
    content()
  }
}

@Composable
fun MangaListItemTitle(
  modifier: Modifier = Modifier,
  text: String,
  maxLines: Int = 1,
  fontWeight: FontWeight = FontWeight.Normal
) {
  Text(
    modifier = modifier,
    text = text,
    maxLines = maxLines,
    overflow = TextOverflow.Ellipsis,
    style = MaterialTheme.typography.body2,
    fontWeight = fontWeight
  )
}

@Composable
fun MangaListItemSubtitle(
  modifier: Modifier = Modifier,
  text: String
) {
  Text(
    modifier = modifier,
    text = text,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
    style = MaterialTheme.typography.caption
  )
}
