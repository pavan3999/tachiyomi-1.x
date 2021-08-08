/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import tachiyomi.domain.library.model.LibraryManga
import tachiyomi.ui.core.coil.rememberMangaCover
import tachiyomi.ui.core.util.Typefaces

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryMangaCompactGrid(
  library: List<LibraryManga>,
  selectedManga: List<Long>,
  columns: Int,
  onClickManga: (LibraryManga) -> Unit = {},
  onLongClickManga: (LibraryManga) -> Unit = {}
) {
  val cells = if (columns > 1) {
    GridCells.Fixed(columns)
  } else {
    GridCells.Adaptive(160.dp)
  }
  LazyVerticalGrid(
    contentPadding = rememberInsetsPaddingValues(
      insets = LocalWindowInsets.current.navigationBars,
      additionalBottom = 8.dp
    ),
    cells = cells,
    modifier = Modifier
      .fillMaxSize()
      .padding(4.dp)
  ) {
    items(library) { manga ->
      LibraryMangaCompactGridItem(
        manga = manga,
        isSelected = manga.id in selectedManga,
        unread = null, // TODO
        downloaded = null, // TODO
        onClick = { onClickManga(manga) },
        onLongClick = { onLongClickManga(manga) }
      )
    }
  }
}

@Composable
private fun LibraryMangaCompactGridItem(
  manga: LibraryManga,
  isSelected: Boolean,
  unread: Int?,
  downloaded: Int?,
  onClick: () -> Unit,
  onLongClick: () -> Unit
) {
  val fontStyle = LocalTextStyle.current.merge(
    TextStyle(letterSpacing = 0.sp, fontFamily = Typefaces.ptSansFont, fontSize = 14.sp)
  )

  Box(
    modifier = Modifier
      .selectedBackground(isSelected)
      .combinedClickable(onClick = onClick, onLongClick = onLongClick)
      .padding(4.dp)
      .fillMaxWidth()
      .aspectRatio(3f / 4f)
      .clip(MaterialTheme.shapes.medium)
  ) {
    Image(
      painter = rememberImagePainter(rememberMangaCover(manga)),
      contentDescription = null,
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop
    )
    Box(
      modifier = Modifier
        .fillMaxSize()
        .then(shadowGradient)
    )
    Text(
      text = manga.title,
      color = Color.White,
      style = fontStyle,
      maxLines = 2,
      modifier = Modifier
        .align(Alignment.BottomStart)
        .padding(8.dp)
    )
    LibraryMangaBadges(
      unread = unread,
      downloaded = downloaded,
      modifier = Modifier.padding(4.dp)
    )
  }
}

private val shadowGradient = Modifier.drawWithCache {
  val gradient = Brush.linearGradient(
    0.75f to Color.Transparent,
    1.0f to Color(0xAA000000),
    start = Offset(0f, 0f),
    end = Offset(0f, size.height)
  )
  onDrawBehind {
    drawRect(gradient)
  }
}

private fun Modifier.selectedBackground(isSelected: Boolean) = composed {
  if (isSelected) {
    background(MaterialTheme.colors.onBackground.copy(alpha = 0.2f))
  } else {
    this
  }
}
