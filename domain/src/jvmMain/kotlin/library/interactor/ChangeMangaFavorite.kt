/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.library.interactor

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import tachiyomi.core.db.Transactions
import tachiyomi.domain.library.service.LibraryCovers
import tachiyomi.domain.library.service.LibraryPreferences
import tachiyomi.domain.library.service.MangaCategoryRepository
import tachiyomi.domain.manga.model.Manga
import tachiyomi.domain.manga.model.MangaUpdate
import tachiyomi.domain.manga.service.MangaRepository
import javax.inject.Inject

class ChangeMangaFavorite @Inject internal constructor(
  private val mangaRepository: MangaRepository,
  private val mangaCategoryRepository: MangaCategoryRepository,
  private val libraryPreferences: LibraryPreferences,
  private val libraryCovers: LibraryCovers,
  private val transactions: Transactions,
  private val setCategoriesForMangas: SetCategoriesForMangas
) {

  suspend fun await(manga: Manga): Result = withContext(NonCancellable) f@{
    val now = System.currentTimeMillis()
    val nowFavorite = !manga.favorite
    val update = if (nowFavorite) {
      MangaUpdate(id = manga.id, favorite = true, dateAdded = now)
    } else {
      MangaUpdate(id = manga.id, favorite = false)
    }

    try {
      transactions.run {
        mangaRepository.updatePartial(update)

        if (nowFavorite) {
          val defaultCategory = libraryPreferences.defaultCategory().get()
          val result = setCategoriesForMangas.await(listOf(defaultCategory), listOf(manga.id))
          if (result is SetCategoriesForMangas.Result.InternalError) {
            throw result.error
          }
        } else {
          mangaCategoryRepository.deleteForManga(manga.id)
        }
      }
    } catch (e: Exception) {
      return@f Result.InternalError(e)
    }

    if (!nowFavorite) {
      try {
        libraryCovers.delete(manga.id)
      } catch (e: Exception) {
        // Ignore this exception
      }
    }

    Result.Success
  }

  sealed class Result {
    object Success : Result()
    data class InternalError(val error: Throwable) : Result()
  }

}
