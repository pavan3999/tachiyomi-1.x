/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.reader

import kotlinx.coroutines.launch
import tachiyomi.core.di.Inject
import tachiyomi.domain.history.interactor.UpsertHistory
import tachiyomi.domain.manga.interactor.GetChapters
import tachiyomi.ui.core.viewmodel.BaseViewModel

class ReaderViewModel @Inject constructor(
  params: Params,
  getChapters: GetChapters,
  upsertHistory: UpsertHistory,
) : BaseViewModel() {

  init {
    scope.launch {
      getChapters.await(params.chapterId)?.let { chapter ->
        upsertHistory.await(chapter.mangaId, chapter.id)
      }
    }
  }

  data class Params(val chapterId: Long)
}
