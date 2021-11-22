/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.manga

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.LoadingScreen
import tachiyomi.ui.core.components.SwipeRefresh
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.components.TransparentStatusBar
import tachiyomi.ui.core.components.rememberSwipeRefreshState
import tachiyomi.ui.core.viewmodel.viewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MangaScreen(
  mangaId: Long,
  navigateUp: () -> Unit,
  openChapter: (Long) -> Unit,
  openTrack: () -> Unit,
  openWebView: (Long, String) -> Unit,
) {
  val vm = viewModel<MangaViewModel, MangaViewModel.Params>(
    initialState = { MangaViewModel.Params(mangaId) }
  )

  val manga = vm.manga
  if (manga == null) {
    // TODO: loading UX
    TransparentStatusBar {
      Column {
        Toolbar(
          title = {},
          navigationIcon = { BackIconButton(navigateUp) },
          contentColor = MaterialTheme.colors.onBackground,
          backgroundColor = Color.Transparent,
          elevation = 0.dp
        )
        LoadingScreen()
      }
    }
    return
  }

  TransparentStatusBar {
    SwipeRefresh(
      state = rememberSwipeRefreshState(vm.isRefreshing),
      onRefresh = { vm.updateManga(metadata = true, chapters = true, tracking = true) }
    ) {
      LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
          MangaInfoHeader(
            manga,
            vm.source,
            vm.expandedSummary,
            navigateUp,
            onFavorite = { vm.toggleFavorite() },
            onTracking = openTrack,
            onWebView = {
              val url = URLEncoder.encode(manga.key, StandardCharsets.UTF_8.toString())
              openWebView(manga.sourceId, url)
            },
            onToggle = { vm.toggleExpandedSummary() },
          )
        }

        item {
          ChapterHeader(vm.chapters, {})
        }

        items(vm.chapters) { chapter ->
          ChapterRow(
            chapter = chapter,
            isDownloaded = false,
            onClick = { openChapter(chapter.id) },
            onDownloadClick = {},
            onDeleteClick = {},
          )
        }
      }
    }
  }
}
