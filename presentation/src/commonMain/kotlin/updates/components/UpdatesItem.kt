/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.updates.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.manga.MangaListItem
import tachiyomi.ui.core.manga.MangaListItemColumn
import tachiyomi.ui.core.manga.MangaListItemImage
import tachiyomi.ui.core.manga.MangaListItemSubtitle
import tachiyomi.ui.core.manga.MangaListItemTitle
import tachiyomi.ui.core.manga.rememberMangaCover
import tachiyomi.ui.core.modifiers.selectedBackground
import tachiyomi.domain.updates.model.UpdatesManga as Manga

@Composable
fun UpdatesItem(
  manga: Manga,
  isSelected: Boolean,
  onClickItem: (Manga) -> Unit,
  onLongClickItem: (Manga) -> Unit,
  onClickCover: (Manga) -> Unit,
  onClickDownload: (Manga) -> Unit
) {
  val alpha = if (manga.read) 0.38f else 1f

  MangaListItem(
    modifier = Modifier
      .combinedClickable(
        onClick = { onClickItem(manga) },
        onLongClick = { onLongClickItem(manga) }
      )
      .selectedBackground(isSelected)
      .height(56.dp)
      .fillMaxWidth()
      .padding(end = 4.dp)
  ) {
    MangaListItemImage(
      modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
        .clip(MaterialTheme.shapes.medium)
        .clickable { onClickCover(manga) },
      mangaCover = rememberMangaCover(manga)
    )
    MangaListItemColumn(
      modifier = Modifier
        .weight(1f)
        .padding(start = 16.dp)
        .alpha(alpha)
    ) {
      MangaListItemTitle(
        text = manga.title,
        fontWeight = FontWeight.SemiBold
      )
      MangaListItemSubtitle(
        text = localize(MR.strings.updates_subtitle, manga.number, manga.name)
      )
    }
    // TODO Replace with Download Composable when that is implemented
    IconButton(onClick = { onClickDownload(manga) }) {
      Icon(imageVector = Icons.Outlined.Download, contentDescription = "")
    }
  }
}
