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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChromeReaderMode
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.CollectionsBookmark
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.GetApp
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.Composable
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.prefs.PreferenceRow

@Composable
fun SettingsScreen(
  openSettingsGeneral: () -> Unit,
  openSettingsAppearance: () -> Unit,
  openSettingsLibrary: () -> Unit,
  openSettingsReader: () -> Unit,
  openSettingsDownloads: () -> Unit,
  openSettingsTracking: () -> Unit,
  openSettingsBrowse: () -> Unit,
  openSettingsBackup: () -> Unit,
  openSettingsSecurity: () -> Unit,
  openSettingsAdvanced: () -> Unit,
  navigateUp: () -> Unit
) {
  Column {
    Toolbar(
      title = { Text(localize(MR.strings.settings_label)) },
      navigationIcon = { BackIconButton(navigateUp) },
    )
    LazyColumn {
      item {
        PreferenceRow(
          title = MR.strings.general_label,
          icon = Icons.Outlined.Tune,
          onClick = openSettingsGeneral,
        )
      }
      item {
        PreferenceRow(
          title = MR.strings.appearance_label,
          icon = Icons.Outlined.Palette,
          onClick = openSettingsAppearance,
        )
      }
      item {
        PreferenceRow(
          title = MR.strings.library_label,
          icon = Icons.Outlined.CollectionsBookmark,
          onClick = openSettingsLibrary,
        )
      }
      item {
        PreferenceRow(
          title = MR.strings.reader_label,
          icon = Icons.Outlined.ChromeReaderMode,
          onClick = openSettingsReader,
        )
      }
      item {
        PreferenceRow(
          title = MR.strings.downloads_label,
          icon = Icons.Outlined.GetApp,
          onClick = openSettingsDownloads,
        )
      }
      item {
        PreferenceRow(
          title = MR.strings.tracking_label,
          icon = Icons.Outlined.Sync,
          onClick = openSettingsTracking,
        )
      }
      item {
        PreferenceRow(
          title = MR.strings.browse_label,
          icon = Icons.Outlined.Explore,
          onClick = openSettingsBrowse,
        )
      }
      item {
        PreferenceRow(
          title = MR.strings.backup_label,
          icon = Icons.Outlined.Restore,
          onClick = openSettingsBackup,
        )
      }
      item {
        PreferenceRow(
          title = MR.strings.security_label,
          icon = Icons.Outlined.Security,
          onClick = openSettingsSecurity,
        )
      }
      item {
        PreferenceRow(
          title = MR.strings.advanced_label,
          icon = Icons.Outlined.Code,
          onClick = openSettingsAdvanced,
        )
      }
    }
  }
}
