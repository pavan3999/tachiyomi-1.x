/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import tachiyomi.core.di.Inject
import tachiyomi.domain.library.interactor.GetLibraryCategory
import tachiyomi.domain.library.interactor.GetUserCategories
import tachiyomi.domain.library.interactor.SetCategoriesForMangas
import tachiyomi.domain.library.interactor.UpdateLibraryCategory
import tachiyomi.domain.library.model.LibraryManga
import tachiyomi.domain.library.service.LibraryPreferences
import tachiyomi.ui.core.viewmodel.BaseViewModel

class LibraryViewModel @Inject constructor(
  private val state: LibraryStateImpl,
  private val getUserCategories: GetUserCategories,
  private val getLibraryCategory: GetLibraryCategory,
  private val setCategoriesForMangas: SetCategoriesForMangas,
  private val libraryPreferences: LibraryPreferences,
  private val updateLibraryCategory: UpdateLibraryCategory
) : BaseViewModel(), LibraryState by state {

  var lastUsedCategory by libraryPreferences.lastUsedCategory().asState()
  val filters by libraryPreferences.filters().asState()
  val sorting by libraryPreferences.sorting().asState()
  val displayMode by libraryPreferences.displayMode().asState()
  val showCategoryTabs by libraryPreferences.showCategoryTabs().asState()
  val showCountInCategory by libraryPreferences.showCountInCategory().asState()

  private val loadedManga = mutableMapOf<Long, List<LibraryManga>>()

  init {
    libraryPreferences.showAllCategory().stateIn(scope)
      .flatMapLatest { showAll ->
        getUserCategories.subscribe(showAll)
          .onEach { categories ->
            val lastCategoryId = lastUsedCategory
            val index = categories.indexOfFirst { it.id == lastCategoryId }.takeIf { it != -1 } ?: 0

            state.categories = categories
            state.selectedCategoryIndex = index
          }
      }
      .launchIn(scope)
  }

  fun setSelectedPage(index: Int) {
    if (index == selectedCategoryIndex) return
    val categories = categories
    val category = categories.getOrNull(index) ?: return
    state.selectedCategoryIndex = index
    lastUsedCategory = category.id
  }

  @Composable
  fun getLibraryForCategoryIndex(categoryIndex: Int): State<List<LibraryManga>> {
    val scope = rememberCoroutineScope()
    val categoryId = categories[categoryIndex].id

    // TODO(inorichi): this approach with a shared flow doesn't look too bad but maybe there's a
    //  better way todo this in a compose world
    val unfiltered = remember(sorting, filters) {
      getLibraryCategory.subscribe(categoryId, sorting, filters)
        .shareIn(scope, SharingStarted.WhileSubscribed(1000), 1)
    }

    return remember(sorting, filters, searchQuery) {
      val query = searchQuery
      if (query.isNullOrBlank()) {
        unfiltered
      } else {
        unfiltered.map { mangas ->
          mangas.filter { it.title.contains(query, true) }
        }
      }
        .onEach { loadedManga[categoryId] = it }
        .onCompletion { loadedManga.remove(categoryId) }
    }.collectAsState(emptyList())
  }

  fun getColumnsForOrientation(isLandscape: Boolean, scope: CoroutineScope): StateFlow<Int> {
    return if (isLandscape) {
      libraryPreferences.columnsInLandscape()
    } else {
      libraryPreferences.columnsInPortrait()
    }.stateIn(scope)
  }

  fun toggleManga(manga: LibraryManga) {
    if (manga.id in selectedManga) {
      state.selectedManga.remove(manga.id)
    } else {
      state.selectedManga.add(manga.id)
    }
  }

  fun unselectAll() {
    state.selectedManga.clear()
  }

  fun selectAllInCurrentCategory() {
    val mangaInCurrentCategory = loadedManga[selectedCategory?.id] ?: return
    val currentSelected = selectedManga.toList()
    val mangaIds = mangaInCurrentCategory.map { it.id }.filter { it !in currentSelected }
    state.selectedManga.addAll(mangaIds)
  }

  fun flipAllInCurrentCategory() {
    val mangaInCurrentCategory = loadedManga[selectedCategory?.id] ?: return
    val currentSelected = selectedManga.toList()
    val (toRemove, toAdd) = mangaInCurrentCategory.map { it.id }.partition { it in currentSelected }
    state.selectedManga.removeAll(toRemove)
    state.selectedManga.addAll(toAdd)
  }

  fun updateLibrary() {
    // TODO(inorichi): For now it only updates the selected category, not the ones selected for
    //  global updates
    val categoryId = selectedCategory?.id ?: return
    updateLibraryCategory.enqueue(categoryId)
  }

  fun changeCategoriesForSelectedManga() {
    // TODO
  }

  fun toggleReadSelectedManga(read: Boolean) {
    // TODO
  }

  fun downloadSelectedManga() {
    // TODO
  }

  fun deleteDownloadsSelectedManga() {
    // TODO
  }

}
