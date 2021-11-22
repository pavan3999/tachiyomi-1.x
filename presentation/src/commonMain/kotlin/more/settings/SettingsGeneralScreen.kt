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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import tachiyomi.core.di.Inject
import tachiyomi.core.util.DateTimeFormatter
import tachiyomi.core.util.format
import tachiyomi.domain.ui.UiPreferences
import tachiyomi.domain.ui.model.StartScreen
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.prefs.ChoicePreference
import tachiyomi.ui.core.prefs.SwitchPreference
import tachiyomi.ui.core.viewmodel.BaseViewModel
import tachiyomi.ui.core.viewmodel.viewModel

class SettingsGeneralViewModel @Inject constructor(
  uiPreferences: UiPreferences
) : BaseViewModel() {

  val startScreen = uiPreferences.startScreen().asState()
  val confirmExit = uiPreferences.confirmExit().asState()
  val hideBottomBarOnScroll = uiPreferences.hideBottomBarOnScroll().asState()
  val language = uiPreferences.language().asState()
  val dateFormat = uiPreferences.dateFormat().asState()

  private val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

  @Composable
  fun getLanguageChoices(): Map<String, String> {
    val currentLocaleDisplayName = Locale.current.toLanguageTag()
    return mapOf(
      "" to "${localize(MR.strings.system_default)} ($currentLocaleDisplayName)"
    )
  }

  @Composable
  fun getDateChoices(): Map<String, String> {
    return mapOf(
      "" to localize(MR.strings.system_default),
      "MM/dd/yy" to "MM/dd/yy",
      "dd/MM/yy" to "dd/MM/yy",
      "yyyy-MM-dd" to "yyyy-MM-dd"
    ).mapValues { "${it.value} (${getFormattedDate(it.key)})" }
  }

  private fun getFormattedDate(prefValue: String): String {
//    return when (prefValue) {
//      "" -> DateFormat.getDateInstance(DateFormat.SHORT)
//      else -> SimpleDateFormat(prefValue, Locale.getDefault())
//    }.format(now.time)
    val formatter = when (prefValue) {
      "" -> DateTimeFormatter("dd/MM/yy") // TODO get SHORT format for current locale
      else -> DateTimeFormatter(prefValue)
    }
    return now.format(formatter)
  }
}

@Composable
fun SettingsGeneralScreen(
  navigateUp: () -> Unit
) {
  val vm = viewModel<SettingsGeneralViewModel>()
  Column {
    Toolbar(
      title = { Text(localize(MR.strings.general_label)) },
      navigationIcon = { BackIconButton(navigateUp) }
    )
    LazyColumn {
      item {
        ChoicePreference(
          preference = vm.startScreen,
          title = MR.strings.start_screen,
          choices = mapOf(
            StartScreen.Library to MR.strings.library_label,
            StartScreen.Updates to MR.strings.updates_label,
            StartScreen.History to MR.strings.history_label,
            StartScreen.Browse to MR.strings.browse_label,
            StartScreen.More to MR.strings.more_label,
          )
        )
      }
      item {
        SwitchPreference(preference = vm.confirmExit, title = MR.strings.confirm_exit)
      }
      item {
        SwitchPreference(
          preference = vm.hideBottomBarOnScroll,
          title = MR.strings.hide_bottom_bar_on_scroll
        )
      }
      settingsGeneralManageNotifications()
      item {
        Divider()
      }
      item {
        ChoicePreference(
          preference = vm.language,
          title = localize(MR.strings.language),
          choices = vm.getLanguageChoices(),
        )
      }
      item {
        ChoicePreference(
          preference = vm.dateFormat,
          title = localize(MR.strings.date_format),
          choices = vm.getDateChoices()
        )
      }
    }
  }
}

internal expect fun LazyListScope.settingsGeneralManageNotifications()
