/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.history.model

import tachiyomi.domain.manga.model.Chapter
import tachiyomi.domain.manga.model.Manga

data class HistoryWithRelations(
  val history: History,
  val manga: Manga,
  val chapter: Chapter,
  val date: String
)
