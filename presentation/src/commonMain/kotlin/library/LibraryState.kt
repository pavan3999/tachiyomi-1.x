/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library

import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import tachiyomi.domain.library.model.CategoryWithCount

@Stable
interface LibraryState {
  var sheetPage: Int
  var searchQuery: String?
  val categories: List<CategoryWithCount>
  val selectedCategoryIndex: Int
  val selectedManga: List<Long>
  var showSheet: Boolean

  val selectionMode: Boolean
  val selectedCategory: CategoryWithCount?

  companion object {
    val Saver: Saver<LibraryState, Any> = listSaver(
      save = {
        listOf(
          it.sheetPage,
          it.searchQuery ?: "<null>"
        )
      },
      restore = {
        @Suppress("CAST_NEVER_SUCCEEDS")
        LibraryState(
          it[0] as Int,
          (it[1] as String).takeIf { it != "<null>" }
        )
      }
    )
  }
}

fun LibraryState(
  sheetPage: Int = 0,
  searchQuery: String? = null
): LibraryState {
  return LibraryStateImpl(sheetPage, searchQuery)
}

class LibraryStateImpl(
  sheetPage: Int,
  searchQuery: String?,
) : LibraryState {

  override var sheetPage by mutableStateOf(sheetPage)
  override var searchQuery by mutableStateOf(searchQuery)
  override var categories by mutableStateOf(
    emptyList<CategoryWithCount>(), referentialEqualityPolicy()
  )
  override var selectedCategoryIndex by mutableStateOf(0)
  override var selectedManga = mutableStateListOf<Long>()
  override var showSheet by mutableStateOf(false)

  override val selectionMode by derivedStateOf { selectedManga.isNotEmpty() }
  override val selectedCategory by derivedStateOf { categories.getOrNull(selectedCategoryIndex) }
}
