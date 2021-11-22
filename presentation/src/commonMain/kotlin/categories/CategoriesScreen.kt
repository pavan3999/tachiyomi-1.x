/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.categories

import androidx.compose.animation.Crossfade
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.categories.CategoriesViewModel.Dialog
import tachiyomi.ui.categories.components.CategoriesContent
import tachiyomi.ui.categories.components.CategoriesEmptyScreen
import tachiyomi.ui.categories.components.CreateCategoryDialog
import tachiyomi.ui.categories.components.DeleteCategoryDialog
import tachiyomi.ui.categories.components.RenameCategoryDialog
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.LoadingScreen
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.viewmodel.viewModel

@Composable
fun CategoriesScreen(
  navigateUp: () -> Unit
) {
  val vm = viewModel<CategoriesViewModel, CategoriesState>(
    initialState = {
      CategoriesState()
    }
  )

  Scaffold(
    topBar = {
      Toolbar(
        title = { Text(localize(MR.strings.categories_label)) },
        navigationIcon = { BackIconButton(navigateUp) }
      )
    }
  ) {
    Crossfade(targetState = Pair(vm.isLoading, vm.isEmpty)) { (isLoading, isEmpty) ->
      when {
        isLoading -> LoadingScreen()
        isEmpty -> CategoriesEmptyScreen(vm)
        else -> CategoriesContent(
          state = vm,
          onMoveUp = { vm.moveUp(it) },
          onMoveDown = { vm.moveDown(it) }
        )
      }
    }
  }

  val dismissRequest = { vm.dialog = null }
  when (val dialog = vm.dialog) {
    Dialog.Create -> {
      CreateCategoryDialog(
        onDismissRequest = dismissRequest,
        onCreate = { vm.createCategory(it) }
      )
    }
    is Dialog.Rename -> {
      val category = dialog.category
      RenameCategoryDialog(
        category = category,
        onDismissRequest = dismissRequest,
        onRename = { vm.renameCategory(category, it) }
      )
    }
    is Dialog.Delete -> {
      val category = dialog.category
      DeleteCategoryDialog(
        category = category,
        onDismissRequest = dismissRequest,
        onDelete = { vm.deleteCategory(category) }
      )
    }
  }
}

