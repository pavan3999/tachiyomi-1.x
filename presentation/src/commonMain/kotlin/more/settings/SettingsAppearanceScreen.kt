/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.more.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tachiyomi.core.di.Inject
import tachiyomi.domain.ui.UiPreferences
import tachiyomi.domain.ui.model.ThemeMode
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.prefs.ChoicePreference
import tachiyomi.ui.core.prefs.ColorPreference
import tachiyomi.ui.core.theme.AppColors
import tachiyomi.ui.core.theme.CustomizableAppColorsPreferenceState
import tachiyomi.ui.core.theme.Theme
import tachiyomi.ui.core.theme.asState
import tachiyomi.ui.core.theme.getDarkColors
import tachiyomi.ui.core.theme.getLightColors
import tachiyomi.ui.core.theme.themes
import tachiyomi.ui.core.viewmodel.BaseViewModel
import tachiyomi.ui.core.viewmodel.viewModel

class ThemesViewModel @Inject constructor(
  private val uiPreferences: UiPreferences,
) : BaseViewModel() {

  val themeMode = uiPreferences.themeMode().asState()
  val lightTheme = uiPreferences.lightTheme().asState()
  val darkTheme = uiPreferences.darkTheme().asState()
  val lightColors = uiPreferences.getLightColors().asState(scope)
  val darkColors = uiPreferences.getDarkColors().asState(scope)

  @Composable
  fun getCustomizedColors(): CustomizableAppColorsPreferenceState {
    return if (MaterialTheme.colors.isLight) lightColors else darkColors
  }
}

@Composable
fun SettingsAppearance(
  navigateUp: () -> Unit
) {
  val vm = viewModel<ThemesViewModel>()

  val customizedColors = vm.getCustomizedColors()
  val isLight = MaterialTheme.colors.isLight
  val themesForCurrentMode = remember(isLight) {
    themes.filter { it.materialColors.isLight == isLight }
  }

  Column {
    Toolbar(
      title = { Text(localize(MR.strings.appearance_label)) },
      navigationIcon = { BackIconButton(navigateUp) },
    )
    LazyColumn {
      item {
        ChoicePreference(
          preference = vm.themeMode,
          choices = mapOf(
            ThemeMode.System to MR.strings.follow_system_settings,
            ThemeMode.Light to MR.strings.light,
            ThemeMode.Dark to MR.strings.dark
          ),
          title = MR.strings.theme
        )
      }
      item {
        Text(
          "Preset themes",
          modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp)
        )
      }
      item {
        LazyRow(modifier = Modifier.padding(horizontal = 8.dp)) {
          items(themesForCurrentMode) { theme ->
            ThemeItem(theme, onClick = {
              (if (isLight) vm.lightTheme else vm.darkTheme).value = it.id
              customizedColors.primaryState.value = it.materialColors.primary
              customizedColors.secondaryState.value = it.materialColors.secondary
              customizedColors.barsState.value = it.extraColors.bars
            })
          }
        }
      }
      item {
        ColorPreference(
          preference = customizedColors.primaryState,
          title = "Color primary",
          subtitle = "Displayed most frequently across your app",
          unsetColor = MaterialTheme.colors.primary
        )
      }
      item {
        ColorPreference(
          preference = customizedColors.secondaryState,
          title = "Color secondary",
          subtitle = "Accents select parts of the UI",
          unsetColor = MaterialTheme.colors.secondary
        )
      }
      item {
        ColorPreference(
          preference = customizedColors.barsState,
          title = "Toolbar color",
          unsetColor = AppColors.current.bars
        )
      }
    }
  }
}

@Composable
private fun ThemeItem(
  theme: Theme,
  onClick: (Theme) -> Unit
) {
  val borders = MaterialTheme.shapes.small
  val borderColor = if (theme.materialColors.isLight) {
    Color.Black.copy(alpha = 0.25f)
  } else {
    Color.White.copy(alpha = 0.15f)
  }
  Surface(
    elevation = 4.dp, color = theme.materialColors.background, shape = borders,
    modifier = Modifier
      .size(100.dp, 160.dp)
      .padding(8.dp)
      .border(1.dp, borderColor, borders)
      .clickable(onClick = { onClick(theme) })
  ) {
    Column {
      Toolbar(
        modifier = Modifier.requiredHeight(24.dp), title = {},
        backgroundColor = theme.extraColors.bars
      )
      Box(
        Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(6.dp)
      ) {
        Text("Text", fontSize = 11.sp)
        Button(
          onClick = {},
          enabled = false,
          contentPadding = PaddingValues(),
          modifier = Modifier
            .align(Alignment.BottomStart)
            .size(40.dp, 20.dp),
          content = {},
          colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = theme.materialColors.primary
          )
        )
        Surface(
          Modifier
            .size(24.dp)
            .align(Alignment.BottomEnd),
          shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
          color = theme.materialColors.secondary,
          elevation = 6.dp,
          content = { }
        )
      }
      BottomAppBar(Modifier.requiredHeight(24.dp), backgroundColor = theme.extraColors.bars) {
      }
    }
  }
}
