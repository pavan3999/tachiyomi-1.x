/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.browse.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tachiyomi.domain.manga.model.Manga
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.LoadingScreen
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.image.rememberImagePainter
import tachiyomi.ui.core.manga.MangaCover
import tachiyomi.ui.core.manga.rememberMangaCover
import tachiyomi.ui.core.theme.Typefaces
import tachiyomi.ui.core.viewmodel.viewModel

@Composable
fun CatalogScreen(
  sourceId: Long,
  navigateUp: () -> Unit,
  openManga: (Long) -> Unit
) {
  val vm = viewModel<CatalogViewModel, CatalogViewModel.Params>(
    initialState = { CatalogViewModel.Params(sourceId) }
  )

  DisposableEffect(Unit) {
    vm.getNextPage()

    onDispose { }
  }

  val catalog = vm.catalog
  Scaffold(
    topBar = {
      val title = catalog?.name ?: "Catalog not found"
      Toolbar(
        title = { Text(title) },
        navigationIcon = { BackIconButton(navigateUp) },
      )
    }
  ) {
    if (catalog == null) {
      LoadingScreen()
    } else {
      MangaTable(
        mangas = vm.mangas,
        isLoading = vm.isRefreshing,
        hasNextPage = vm.hasNextPage,
        loadNextPage = { vm.getNextPage() },
        onClickManga = { openManga(it.id) },
      )
    }
  }
}

@Composable
private fun MangaTable(
  mangas: List<Manga>,
  isLoading: Boolean = false,
  hasNextPage: Boolean = false,
  loadNextPage: () -> Unit = {},
  onClickManga: (Manga) -> Unit = {}
) {
  if (mangas.isEmpty()) {
    LoadingScreen()
  } else {
    Column {
      // TODO: this should happen automatically on scroll
      Button(onClick = { loadNextPage() }, enabled = hasNextPage) {
        Text(text = if (isLoading) "Loading..." else "Load next page")
      }

      LazyVerticalGrid(GridCells.Adaptive(160.dp)) {
        items(mangas) { manga ->
          MangaGridItem(
            title = manga.title,
            cover = rememberMangaCover(manga),
            onClick = { onClickManga(manga) }
          )
        }
      }
    }
  }
}

@Composable
fun MangaGridItem(
  title: String,
  cover: MangaCover,
  onClick: () -> Unit = {},
) {
  val fontStyle = LocalTextStyle.current.merge(
    TextStyle(letterSpacing = 0.sp, fontFamily = Typefaces.condensed, fontSize = 14.sp)
  )

  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(3f / 4f)
      .padding(4.dp)
      .clickable(onClick = onClick),
    elevation = 4.dp,
    shape = RoundedCornerShape(4.dp)
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      Image(
        painter = rememberImagePainter(cover),
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
        text = title,
        color = Color.White,
        style = fontStyle,
        modifier = Modifier
          .wrapContentHeight(Alignment.CenterVertically)
          .align(Alignment.BottomStart)
          .padding(8.dp)
      )
    }
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
