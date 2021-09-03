/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

@file:Suppress("PackageDirectoryMismatch")

package tachiyomi.domain.history.model

import androidx.room.Embedded
import androidx.room.Relation
import tachiyomi.domain.manga.model.Chapter
import tachiyomi.domain.manga.model.Manga

data class HistoryWithRelations(
  @Embedded val history: History,
  @Relation(
    parentColumn = "mangaId",
    entityColumn = "id"
  )
  val manga: Manga,
  @Relation(
    parentColumn = "chapterId",
    entityColumn = "id"
  )
  val chapter: Chapter,
  val date: String
)