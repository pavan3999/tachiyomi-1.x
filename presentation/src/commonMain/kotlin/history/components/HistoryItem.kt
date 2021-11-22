/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.history.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import tachiyomi.core.util.DateTimeFormatter
import tachiyomi.core.util.format
import tachiyomi.domain.history.model.HistoryWithRelations
import tachiyomi.ui.core.manga.MangaListItem
import tachiyomi.ui.core.manga.MangaListItemColumn
import tachiyomi.ui.core.manga.MangaListItemImage
import tachiyomi.ui.core.manga.MangaListItemSubtitle
import tachiyomi.ui.core.manga.MangaListItemTitle
import tachiyomi.ui.core.manga.rememberMangaCover

@Composable
fun HistoryItem(
  history: HistoryWithRelations,
  onClickItem: (HistoryWithRelations) -> Unit,
  onClickDelete: (HistoryWithRelations) -> Unit,
  onClickPlay: (HistoryWithRelations) -> Unit
) {
  MangaListItem(
    modifier = Modifier
      .clickable { onClickItem(history) }
      .height(80.dp)
      .fillMaxWidth()
      .padding(end = 4.dp),
  ) {
    MangaListItemImage(
      modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(3f / 4f)
        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
        .clip(MaterialTheme.shapes.medium),
      mangaCover = rememberMangaCover(history)
    )
    MangaListItemColumn(
      modifier = Modifier
        .weight(1f)
        .padding(start = 16.dp, end = 8.dp)
    ) {
      MangaListItemTitle(
        text = history.mangaTitle,
        maxLines = 2,
        fontWeight = FontWeight.SemiBold
      )

      MangaListItemSubtitle(
        text = "Ch. ${history.chapterNumber} - ${
          history.readAt.toLocalDateTime().format(formatter)
        }"
      )
    }
    IconButton(onClick = { onClickDelete(history) }) {
      Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
    }
    IconButton(onClick = { onClickPlay(history) }) {
      Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "")
    }
  }
}

private val formatter = DateTimeFormatter("HH:mm")

private fun Long.toLocalDateTime(): LocalDateTime {
  return Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
}
