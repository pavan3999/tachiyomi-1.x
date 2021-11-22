/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.categories

import androidx.compose.runtime.Composable
import tachiyomi.domain.library.model.Category
import tachiyomi.domain.library.model.CategoryWithCount
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize

val Category.visibleName
  @Composable
  get() = when (id) {
    Category.ALL_ID -> localize(MR.strings.all_category)
    Category.UNCATEGORIZED_ID -> localize(MR.strings.uncategorized_category)
    else -> name
  }

val CategoryWithCount.visibleName
  @Composable
  get() = category.visibleName
