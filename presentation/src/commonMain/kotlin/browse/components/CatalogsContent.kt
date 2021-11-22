/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.browse.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tachiyomi.domain.catalog.model.Catalog
import tachiyomi.domain.catalog.model.CatalogInstalled
import tachiyomi.domain.catalog.model.CatalogLocal
import tachiyomi.ui.browse.CatalogsState
import tachiyomi.ui.core.components.SwipeRefresh
import tachiyomi.ui.core.components.rememberSwipeRefreshState
import tachiyomi.ui.core.modifiers.rememberNavigationBarsInsetsPaddingValues

@Composable
fun CatalogsContent(
  state: CatalogsState,
  onRefreshCatalogs: () -> Unit,
  onClickCatalog: (Catalog) -> Unit,
  onClickInstall: (Catalog) -> Unit,
  onClickUninstall: (Catalog) -> Unit,
  onClickTogglePinned: (CatalogLocal) -> Unit
) {
  val swipeState = rememberSwipeRefreshState(state.isRefreshing)

  val catalogLocalItem: LazyListScope.(CatalogLocal) -> Unit = { catalog ->
    item(key = catalog.sourceId) {
      CatalogItem(
        catalog = catalog,
        installStep = if (catalog is CatalogInstalled) state.installSteps[catalog.pkgName] else null,
        onClick = { onClickCatalog(catalog) },
        onInstall = { onClickInstall(catalog) }.takeIf { catalog.hasUpdate },
        onUninstall = { onClickUninstall(catalog) }.takeIf { catalog is CatalogInstalled },
        onPinToggle = { onClickTogglePinned(catalog) }
      )
    }
  }

  SwipeRefresh(state = swipeState, onRefresh = onRefreshCatalogs) {
    LazyColumn(
      contentPadding = rememberNavigationBarsInsetsPaddingValues(
        additionalBottom = 16.dp
      )
    ) {
      if (state.pinnedCatalogs.isNotEmpty()) {
        item(key = "h1") {
          CatalogsSection(
            text = "Pinned",
            isExpanded = state.expandPinned,
            onClick = { state.expandPinned = !state.expandPinned }
          )
        }
        if (state.expandPinned) {
          for (catalog in state.pinnedCatalogs) {
            catalogLocalItem(catalog)
          }
        }
      }
      if (state.unpinnedCatalogs.isNotEmpty()) {
        item(key = "h2") {
          CatalogsSection(
            text = "Installed",
            isExpanded = state.expandInstalled,
            onClick = { state.expandInstalled = !state.expandInstalled }
          )
        }
        if (state.expandInstalled) {
          for (catalog in state.unpinnedCatalogs) {
            catalogLocalItem(catalog)
          }
        }
      }
      if (state.remoteCatalogs.isNotEmpty()) {
        item(key = "h3") {
          CatalogsSection(
            text = "Available",
            isExpanded = state.expandAvailable,
            onClick = { state.expandAvailable = !state.expandAvailable }
          )
        }
        if (state.expandAvailable) {
          item(key = "langs") {
            LanguageChipGroup(
              choices = state.languageChoices,
              selected = state.selectedLanguage,
              onClick = { state.selectedLanguage = it },
              modifier = Modifier.padding(8.dp)
            )
          }
          items(state.remoteCatalogs, key = { it.sourceId }) { catalog ->
            CatalogItem(
              catalog = catalog,
              installStep = state.installSteps[catalog.pkgName],
              onInstall = { onClickInstall(catalog) }
            )
          }
        }
      }
    }
  }
}
