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
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tachiyomi.domain.catalog.model.Catalog
import tachiyomi.domain.catalog.model.CatalogBundled
import tachiyomi.ui.browse.CatalogsViewModel

// TODO(inorichi): consider creating an interface for the state to pass around that object rather
//  than a viewmodel, or add everything as a parameter
@Composable
fun CatalogsContent(
  vm: CatalogsViewModel,
  onClickCatalog: (Catalog) -> Unit
) {
  LazyColumn {
    if (vm.localCatalogs.any { it.isPinned } || vm.updatableCatalogs.any { it.isPinned }) {
      item(key = "h1") {
        CatalogsHeader(text = "Pinned")
      }
      for (catalog in vm.updatableCatalogs) {
        if (catalog.isPinned) {
          item(key = catalog.sourceId) {
            CatalogItem(
              catalog = catalog,
              installStep = vm.installSteps[catalog.pkgName],
              onClick = { onClickCatalog(catalog) },
              onInstall = { vm.installCatalog(catalog) },
              onUninstall = { vm.uninstallCatalog(catalog) },
              onPinToggle = { vm.togglePinnedCatalog(catalog) }
            )
          }
        }
      }
      for (catalog in vm.localCatalogs) {
        if (catalog.isPinned) {
          item(key = catalog.sourceId) {
            CatalogItem(
              catalog = catalog,
              onClick = { onClickCatalog(catalog) },
              onUninstall = { vm.uninstallCatalog(catalog) }.takeIf { catalog !is CatalogBundled },
              onPinToggle = { vm.togglePinnedCatalog(catalog) }
            )
          }
        }
      }
    }
    if (vm.localCatalogs.any { !it.isPinned } || vm.updatableCatalogs.any { !it.isPinned }) {
      item(key = "h2") {
        CatalogsHeader(text = "Installed")
      }
      for (catalog in vm.updatableCatalogs) {
        if (!catalog.isPinned) {
          item(key = catalog.sourceId) {
            CatalogItem(
              catalog = catalog,
              installStep = vm.installSteps[catalog.pkgName],
              onClick = { onClickCatalog(catalog) },
              onInstall = { vm.installCatalog(catalog) },
              onUninstall = { vm.uninstallCatalog(catalog) },
              onPinToggle = { vm.togglePinnedCatalog(catalog) }
            )
          }
        }
      }
      for (catalog in vm.localCatalogs) {
        if (!catalog.isPinned) {
          item(key = catalog.sourceId) {
            CatalogItem(
              catalog = catalog,
              onClick = { onClickCatalog(catalog) },
              onUninstall = { vm.uninstallCatalog(catalog) }.takeIf { catalog !is CatalogBundled },
              onPinToggle = { vm.togglePinnedCatalog(catalog) }
            )
          }
        }
      }
    }
    if (vm.remoteCatalogs.isNotEmpty()) {
      item(key = "h3") {
        CatalogsHeader("Available")
      }
      item(key = "langs") {
        LanguageChipGroup(
          choices = vm.languageChoices,
          selected = vm.selectedLanguage,
          onClick = { vm.setLanguageChoice(it) },
          modifier = Modifier.padding(8.dp)
        )
      }
      items(vm.remoteCatalogs, key = { it.sourceId }) { catalog ->
        CatalogItem(
          catalog = catalog,
          installStep = vm.installSteps[catalog.pkgName],
          onInstall = { vm.installCatalog(catalog) }
        )
      }
    }
  }
}
