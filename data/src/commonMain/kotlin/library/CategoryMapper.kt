/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data.library

import tachiyomi.domain.library.model.Category
import tachiyomi.domain.library.model.CategoryWithCount

internal val categoryMapper =
  { id: Long, name: String, order: Int, updateInterval: Int, flags: Long ->
    Category(id, name, order, updateInterval, flags)
  }

internal val categoryWithCountMapper =
  { id: Long, name: String, order: Int, updateInterval: Int, flags: Long, count: Long ->
    CategoryWithCount(Category(id, name, order, updateInterval, flags), count.toInt())
  }
