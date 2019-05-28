/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.manga.repository

import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.manga.model.Chapter
import tachiyomi.domain.manga.model.ChapterUpdate

interface ChapterRepository {

  fun subscribeForManga(mangaId: Long): Flow<List<Chapter>>

  fun subscribe(chapterId: Long): Flow<Chapter?>

  fun findForManga(mangaId: Long): List<Chapter>

  fun find(chapterId: Long): Chapter?

  fun find(chapterKey: String, mangaId: Long): Chapter?

  fun save(chapters: List<Chapter>)

  fun savePartial(update: List<ChapterUpdate>)

  fun saveNewOrder(chapters: List<Chapter>)

  fun delete(chapterId: Long)

  fun delete(chapterIds: List<Long>)

}
