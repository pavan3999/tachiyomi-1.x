/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package library.interactor

import tachiyomi.core.di.Inject
import tachiyomi.domain.library.model.Category
import tachiyomi.domain.library.model.CategoryUpdate
import tachiyomi.domain.library.model.DisplayMode
import tachiyomi.domain.library.model.DisplayMode.Companion.set
import tachiyomi.domain.library.service.CategoryRepository
import tachiyomi.domain.library.service.LibraryPreferences

class SetDisplayModeForCategory @Inject constructor(
  private val libraryPreferences: LibraryPreferences,
  private val categoryRepository: CategoryRepository
) {

  suspend fun await(category: Category, displayMode: DisplayMode) {
    if (libraryPreferences.perCategorySettings().get()) {
      val category = category.set(displayMode)
      categoryRepository.updatePartial(
        CategoryUpdate(
          id = category.id,
          flags = category.flags
        )
      )
    } else {
      val flags = libraryPreferences.categoryFlags().set(displayMode)
      categoryRepository.updateAllFlags(flags)
    }
  }

}