/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.browse

import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tachiyomi.core.di.Inject
import tachiyomi.domain.catalog.interactor.GetCatalogsByType
import tachiyomi.domain.catalog.interactor.InstallCatalog
import tachiyomi.domain.catalog.interactor.SyncRemoteCatalogs
import tachiyomi.domain.catalog.interactor.TogglePinnedCatalog
import tachiyomi.domain.catalog.interactor.UninstallCatalog
import tachiyomi.domain.catalog.interactor.UpdateCatalog
import tachiyomi.domain.catalog.model.Catalog
import tachiyomi.domain.catalog.model.CatalogInstalled
import tachiyomi.domain.catalog.model.CatalogLocal
import tachiyomi.domain.catalog.model.CatalogRemote
import tachiyomi.domain.catalog.model.InstallStep
import tachiyomi.ui.core.viewmodel.BaseViewModel

class CatalogsViewModel @Inject constructor(
  private val state: CatalogsStateImpl,
  private val getCatalogsByType: GetCatalogsByType,
  private val installCatalog: InstallCatalog,
  private val uninstallCatalog: UninstallCatalog,
  private val updateCatalog: UpdateCatalog,
  private val syncRemoteCatalogs: SyncRemoteCatalogs,
  private val togglePinnedCatalog: TogglePinnedCatalog
) : BaseViewModel(), CatalogsState by state {

  init {
    scope.launch {
      getCatalogsByType.subscribe(excludeRemoteInstalled = true)
        .collect { (pinned, unpinned, remote) ->
          state.allPinnedCatalogs = pinned
          state.allUnpinnedCatalogs = unpinned
          state.allRemoteCatalogs = remote

          state.languageChoices = getLanguageChoices(remote, pinned + unpinned)
        }
    }

    // Update catalogs whenever the query changes or there's a new update from the backend
    snapshotFlow { state.allPinnedCatalogs.filteredByQuery(searchQuery) }
      .onEach { state.pinnedCatalogs = it }
      .launchIn(scope)
    snapshotFlow { state.allUnpinnedCatalogs.filteredByQuery(searchQuery) }
      .onEach { state.unpinnedCatalogs = it }
      .launchIn(scope)
    snapshotFlow {
      state.allRemoteCatalogs.filteredByQuery(searchQuery).filteredByChoice(selectedLanguage)
    }
      .onEach { state.remoteCatalogs = it }
      .launchIn(scope)
  }

  fun installCatalog(catalog: Catalog) {
    scope.launch {
      val isUpdate = catalog is CatalogInstalled
      val (pkgName, flow) = if (isUpdate) {
        catalog as CatalogInstalled
        catalog.pkgName to updateCatalog.await(catalog)
      } else {
        catalog as CatalogRemote
        catalog.pkgName to installCatalog.await(catalog)
      }
      flow.collect { step ->
        state.installSteps = if (step != InstallStep.Completed) {
          installSteps + (pkgName to step)
        } else {
          installSteps - pkgName
        }
      }
    }
  }

  fun togglePinnedCatalog(catalog: CatalogLocal) {
    scope.launch {
      togglePinnedCatalog.await(catalog)
    }
  }

  fun uninstallCatalog(catalog: Catalog) {
    scope.launch {
      uninstallCatalog.await(catalog as CatalogInstalled)
    }
  }

  fun refreshCatalogs() {
    scope.launch {
      state.isRefreshing = true
      syncRemoteCatalogs.await(true)
      state.isRefreshing = false
    }
  }

  private fun getLanguageChoices(
    remote: List<CatalogRemote>,
    local: List<CatalogLocal>
  ): List<LanguageChoice> {
    val knownLanguages = mutableListOf<LanguageChoice.One>()
    val unknownLanguages = mutableListOf<Language>()

    val languageComparators = UserLanguagesComparator()
      .then(InstalledLanguagesComparator(local))
      .thenBy { it.code }

    remote.asSequence()
      .map { Language(it.lang) }
      .distinct()
      .sortedWith(languageComparators)
      .forEach { code ->
        if (code.toEmoji() != null) {
          knownLanguages.add(LanguageChoice.One(code))
        } else {
          unknownLanguages.add(code)
        }
      }

    val languages = mutableListOf<LanguageChoice>()
    languages.add(LanguageChoice.All)
    languages.addAll(knownLanguages)
    if (unknownLanguages.isNotEmpty()) {
      languages.add(LanguageChoice.Others(unknownLanguages))
    }

    return languages
  }

  private fun <T : Catalog> List<T>.filteredByQuery(query: String?): List<T> {
    return if (query == null) {
      this
    } else {
      filter { it.name.contains(query, true) }
    }
  }

  private fun List<CatalogRemote>.filteredByChoice(choice: LanguageChoice): List<CatalogRemote> {
    return when (choice) {
      LanguageChoice.All -> this
      is LanguageChoice.One -> filter { choice.language.code == it.lang }
      is LanguageChoice.Others -> {
        val codes = choice.languages.map { it.code }
        filter { it.lang in codes }
      }
    }
  }

}
