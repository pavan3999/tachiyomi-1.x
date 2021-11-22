/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.updates

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import tachiyomi.core.di.Inject
import tachiyomi.domain.updates.interactor.GetUpdatesGroupByDate
import tachiyomi.ui.core.viewmodel.BaseViewModel
import tachiyomi.domain.updates.model.UpdatesManga as Manga

typealias UpdateMangaByDate = Map.Entry<LocalDate, List<Manga>>

class UpdatesViewModel @Inject constructor(
  private val state: UpdatesStateImpl,
  getUpdatesGroupByDate: GetUpdatesGroupByDate
) : BaseViewModel(), UpdatesState by state {

  init {
    scope.launch {
      getUpdatesGroupByDate
        .subscribeAll()
        .collectLatest {
          state.isLoading = false
          state.updates = it
        }
    }
  }

  fun unselectAll() {
    state.selection.clear()
  }

  fun selectAll() {
    val allChapterIds = state.updates
      .flatMap(UpdateMangaByDate::value)
      .map(Manga::chapterId)
    state.selection.clear()
    state.selection.addAll(allChapterIds)
  }

  fun flipSelection() {
    val notSelectedIds = state.updates
      .flatMap(UpdateMangaByDate::value)
      .map(Manga::chapterId)
      .filterNot { id -> id in state.selection }
    state.selection.clear()
    state.selection.addAll(notSelectedIds)
  }

  fun updateLibrary() {
    // TODO
  }

  fun toggleManga(manga: Manga) {
    when (val id = manga.chapterId) {
      in state.selection -> state.selection.remove(id)
      else -> state.selection.add(id)
    }
  }
}
