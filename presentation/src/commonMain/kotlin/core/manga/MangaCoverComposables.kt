/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.manga

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import tachiyomi.domain.history.model.HistoryWithRelations
import tachiyomi.domain.library.model.LibraryManga
import tachiyomi.domain.manga.model.Manga
import tachiyomi.domain.updates.model.UpdatesManga

@Composable
fun rememberMangaCover(manga: Manga): MangaCover {
  return remember(manga.id) {
    MangaCover.from(manga)
  }
}

@Composable
fun rememberMangaCover(manga: LibraryManga): MangaCover {
  return remember(manga.id) {
    MangaCover.from(manga)
  }
}

@Composable
fun rememberMangaCover(manga: UpdatesManga): MangaCover {
  return remember(manga.id) {
    MangaCover.from(manga)
  }
}

@Composable
fun rememberMangaCover(history: HistoryWithRelations): MangaCover {
  return remember(history.mangaId) {
    MangaCover.from(history)
  }
}
