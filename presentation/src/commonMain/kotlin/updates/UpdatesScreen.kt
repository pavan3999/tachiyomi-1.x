/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.updates

import androidx.compose.animation.Crossfade
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import tachiyomi.i18n.MR
import tachiyomi.ui.core.components.EmptyScreen
import tachiyomi.ui.core.components.LoadingScreen
import tachiyomi.ui.core.viewmodel.viewModel
import tachiyomi.ui.updates.components.UpdatesContent
import tachiyomi.ui.updates.components.UpdatesToolbar

@Composable
fun UpdatesScreen(
  openChapter: (Long) -> Unit,
  openManga: (Long) -> Unit
) {
  val vm = viewModel<UpdatesViewModel, UpdatesState>(
    initialState = { UpdatesState() }
  )

  Scaffold(
    topBar = {
      UpdatesToolbar(
        state = vm,
        onClickCancelSelection = { vm.unselectAll() },
        onClickSelectAll = { vm.selectAll() },
        onClickFlipSelection = { vm.flipSelection() },
        onClickRefresh = { vm.updateLibrary() }
      )
    }
  ) {
    Crossfade(targetState = Pair(vm.isLoading, vm.isEmpty)) { (isLoading, isEmpty) ->
      when {
        isLoading -> LoadingScreen()
        isEmpty -> EmptyScreen(MR.strings.information_no_updates)
        else -> UpdatesContent(
          state = vm,
          onClickItem = {
            when {
              vm.hasSelection -> vm.toggleManga(it)
              else -> openChapter(it.chapterId)
            }
          },
          onLongClickItem = { vm.toggleManga(it) },
          onClickCover = { openManga(it.id) },
          onClickDownload = { /* TOOD */ }
        )
      }
    }
  }
}
