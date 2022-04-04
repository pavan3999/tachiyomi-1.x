/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.more.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import library.interactor.ResetCategoryFlags
import tachiyomi.core.di.Inject
import tachiyomi.core.util.IO
import tachiyomi.domain.library.service.LibraryPreferences
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.prefs.SwitchPreference
import tachiyomi.ui.core.viewmodel.BaseViewModel
import tachiyomi.ui.core.viewmodel.viewModel

class SettingsLibraryViewModel @Inject constructor(
  libraryPreferences: LibraryPreferences,
  resetCategoryFlags: ResetCategoryFlags
) : BaseViewModel() {

  val showAllCategory = libraryPreferences.showAllCategory().asState()

  val perCategorySettings = libraryPreferences.perCategorySettings()
    .asState { isPerCategorySettings ->
      if (!isPerCategorySettings) {
        scope.launch(Dispatchers.IO) {
          resetCategoryFlags.await()
        }
      }
    }
}

@Composable
fun SettingsLibraryScreen(
  navigateUp: () -> Unit
) {
  val vm = viewModel<SettingsLibraryViewModel>()

  Column {
    Toolbar(
      title = { Text(localize(MR.strings.library_label)) },
      navigationIcon = { BackIconButton(navigateUp) }
    )
    LazyColumn {
      item {
        SwitchPreference(preference = vm.showAllCategory, title = "Show all category")
      }
      item {
        SwitchPreference(preference = vm.perCategorySettings, title = "Per category settings")
      }
    }
  }
}
