/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tachiyomi.ui.core.viewmodel.viewModel
import tachiyomi.ui.library.components.LibraryContent
import tachiyomi.ui.library.components.LibrarySelectionBar
import tachiyomi.ui.library.components.LibrarySheetLayout
import tachiyomi.ui.library.components.LibraryToolbar

@Composable
fun LibraryScreen(
  requestHideBottomNav: (Boolean) -> Unit,
  openManga: (Long) -> Unit
) {
  val vm = viewModel<LibraryViewModel, LibraryState>(
    initialState = { LibraryState() },
    saver = LibraryState.Saver
  )

  LaunchedEffect(vm.selectionMode, vm.showSheet) {
    requestHideBottomNav(vm.selectionMode || vm.showSheet)
  }

  LibrarySheetLayout(
    showSheet = vm.showSheet,
    currentPage = vm.sheetPage,
    onSheetDismissed = { vm.showSheet = false },
    onPageChanged = { vm.sheetPage = it }
  ) {
    Box {
      Column {
        LibraryToolbar(
          state = vm,
          showCategoryTabs = vm.showCategoryTabs,
          showCountInCategory = vm.showCountInCategory,
          onClickRefresh = { vm.updateLibrary() },
          onClickCloseSelection = { vm.unselectAll() },
          onClickSelectAll = { vm.selectAllInCurrentCategory() },
          onClickUnselectAll = { vm.flipAllInCurrentCategory() }
        )
        LibraryContent(
          categories = vm.categories,
          displayMode = vm.displayMode,
          currentPage = vm.selectedCategoryIndex,
          showPageTabs = vm.showCategoryTabs,
          showCountInCategory = vm.showCountInCategory,
          selectedManga = vm.selectedManga,
          getColumnsForOrientation = { vm.getColumnsForOrientation(it, this) },
          getLibraryForPage = { vm.getLibraryForCategoryIndex(it) },
          onClickManga = { manga ->
            if (!vm.selectionMode) {
              openManga(manga.id)
            } else {
              vm.toggleManga(manga)
            }
          },
          onLongClickManga = { vm.toggleManga(it) },
          onPageChanged = { vm.setSelectedPage(it) },
        )
      }
      LibrarySelectionBar(
        visible = vm.selectionMode,
        modifier = Modifier.align(Alignment.BottomCenter),
        onClickChangeCategory = { vm.changeCategoriesForSelectedManga() },
        onClickDownload = { vm.downloadSelectedManga() },
        onClickMarkAsRead = { vm.toggleReadSelectedManga(read = true) },
        onClickMarkAsUnread = { vm.toggleReadSelectedManga(read = false) },
        onClickDeleteDownloads = { vm.deleteDownloadsSelectedManga() }
      )
    }
  }
}
