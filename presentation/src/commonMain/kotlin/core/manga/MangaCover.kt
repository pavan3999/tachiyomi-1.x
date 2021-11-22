/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.manga

import tachiyomi.domain.history.model.HistoryWithRelations
import tachiyomi.domain.library.model.LibraryManga
import tachiyomi.domain.manga.model.Manga
import tachiyomi.domain.updates.model.UpdatesManga

/**
 * Class used to load manga covers with Coil.
 */
class MangaCover(
  val id: Long,
  val sourceId: Long,
  val cover: String,
  val favorite: Boolean
) {

  companion object {

    fun from(manga: Manga): MangaCover {
      return MangaCover(manga.id, manga.sourceId, manga.cover, manga.favorite)
    }

    fun from(manga: LibraryManga): MangaCover {
      return MangaCover(manga.id, manga.sourceId, manga.cover, true)
    }

    fun from(manga: UpdatesManga): MangaCover {
      return MangaCover(manga.id, manga.sourceId, manga.cover, manga.favorite)
    }

    fun from(history: HistoryWithRelations): MangaCover {
      return MangaCover(history.mangaId, history.sourceId, history.cover, history.favorite)
    }
  }

}
