/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data.category

import kotlinx.coroutines.flow.Flow
import tachiyomi.core.di.Inject
import tachiyomi.data.Database
import tachiyomi.data.DatabaseHandler
import tachiyomi.data.library.categoryMapper
import tachiyomi.data.library.categoryWithCountMapper
import tachiyomi.domain.library.model.Category
import tachiyomi.domain.library.model.CategoryUpdate
import tachiyomi.domain.library.model.CategoryWithCount
import tachiyomi.domain.library.service.CategoryRepository

internal class CategoryRepositoryImpl @Inject constructor(
  private val handler: DatabaseHandler
) : CategoryRepository {

  override fun subscribeAll(): Flow<List<Category>> {
    return handler.subscribeToList { categoryQueries.findAll(categoryMapper) }
  }

  override fun subscribeWithCount(): Flow<List<CategoryWithCount>> {
    return handler.subscribeToList { categoryQueries.findAllWithCount(categoryWithCountMapper) }
  }

  override fun subscribeCategoriesOfManga(mangaId: Long): Flow<List<Category>> {
    return handler.subscribeToList { categoryQueries.findForManga(mangaId, categoryMapper) }
  }

  override suspend fun findAll(): List<Category> {
    return handler.awaitList { categoryQueries.findAll(categoryMapper) }
  }

  override suspend fun find(categoryId: Long): Category? {
    return handler.awaitOneOrNull { categoryQueries.findById(categoryId, categoryMapper) }
  }

  override suspend fun findCategoriesOfManga(mangaId: Long): List<Category> {
    return handler.awaitList { categoryQueries.findForManga(mangaId, categoryMapper) }
  }

  override suspend fun insert(category: Category) {
    handler.await { insertBlocking(category) }
  }

  override suspend fun insert(categories: List<Category>) {
    handler.await(inTransaction = true) {
      for (category in categories) {
        insertBlocking(category)
      }
    }
  }

  override suspend fun updatePartial(update: CategoryUpdate) {
    handler.await { updatePartialBlocking(update) }
  }

  override suspend fun updatePartial(updates: Collection<CategoryUpdate>) {
    handler.await(inTransaction = true) {
      for (update in updates) {
        updatePartialBlocking(update)
      }
    }
  }

  override suspend fun delete(categoryId: Long) {
    handler.await { categoryQueries.deleteById(categoryId) }
  }

  override suspend fun delete(categoryIds: Collection<Long>) {
    handler.await(inTransaction = true) {
      for (categoryId in categoryIds) {
        categoryQueries.deleteById(categoryId)
      }
    }
  }

  override suspend fun updateAllFlags(flags: Long?) {
    handler.await {
      categoryQueries.updateAllFlags(flags)
    }
  }

  private fun Database.insertBlocking(category: Category) {
    categoryQueries.insert(
      name = category.name,
      sort = category.order,
      updateInterval = category.updateInterval,
      flags = category.flags
    )
  }

  private fun Database.updatePartialBlocking(update: CategoryUpdate) {
    categoryQueries.update(
      update.name,
      update.order?.toLong(),
      update.updateInterval?.toLong(),
      update.flags,
      id = update.id,
    )
  }

}
