/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.history

import androidx.compose.animation.Crossfade
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import tachiyomi.i18n.MR
import tachiyomi.ui.core.components.EmptyScreen
import tachiyomi.ui.core.components.LoadingScreen
import tachiyomi.ui.core.viewmodel.viewModel
import tachiyomi.ui.history.components.HistoryContent
import tachiyomi.ui.history.components.HistoryToolbar

@Composable
fun HistoryScreen(
  openChapter: (Long) -> Unit,
  openManga: (Long) -> Unit
) {
  val vm = viewModel<HistoryViewModel, HistoryState>(
    initialState = { HistoryState() }
  )

  Scaffold(
    topBar = {
      HistoryToolbar(
        state = vm,
        onClickDeleteAll = { vm.deleteAll() }
      )
    }
  ) {
    Crossfade(targetState = Pair(vm.isLoading, vm.isEmpty)) { (isLoading, isEmpty) ->
      when {
        isLoading -> LoadingScreen()
        isEmpty -> EmptyScreen(MR.strings.information_no_history)
        else -> HistoryContent(
          state = vm,
          onClickItem = { openManga(it.mangaId) },
          onClickDelete = { vm.delete(it) },
          onClickPlay = { openChapter(it.chapterId) }
        )
      }
    }
  }
}
