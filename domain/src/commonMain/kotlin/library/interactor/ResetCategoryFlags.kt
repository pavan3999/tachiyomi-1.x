/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package library.interactor

import tachiyomi.core.di.Inject
import tachiyomi.domain.library.model.DisplayMode
import tachiyomi.domain.library.service.CategoryRepository
import tachiyomi.domain.library.service.LibraryPreferences

class ResetCategoryFlags @Inject constructor(
  private val libraryPreferences: LibraryPreferences,
  private val categoryRepository: CategoryRepository
) {

  suspend fun await() {
    val flags = libraryPreferences.categoryFlags().get()
    categoryRepository.updateAllFlags(flags)
  }

}