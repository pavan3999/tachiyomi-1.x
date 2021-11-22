/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.browse

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import tachiyomi.ui.browse.components.CatalogsContent
import tachiyomi.ui.browse.components.CatalogsToolbar
import tachiyomi.ui.core.viewmodel.viewModel

@Composable
fun CatalogsScreen(
  openCatalog: (Long) -> Unit
) {
  val vm = viewModel<CatalogsViewModel, CatalogsState>(
    initialState = { CatalogsState() }
  )

  Scaffold(
    topBar = {
      CatalogsToolbar(
        searchQuery = vm.searchQuery,
        onClickCloseSearch = { vm.searchQuery = null },
        onChangeSearchQuery = { vm.searchQuery = it }
      )
    }
  ) {
    CatalogsContent(
      state = vm,
      onRefreshCatalogs = { vm.refreshCatalogs() },
      onClickCatalog = { openCatalog(it.sourceId) },
      onClickInstall = { vm.installCatalog(it) },
      onClickUninstall = { vm.uninstallCatalog(it) },
      onClickTogglePinned = { vm.togglePinnedCatalog(it) }
    )
  }
}
