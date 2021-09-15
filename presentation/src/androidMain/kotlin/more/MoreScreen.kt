/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.outlined.GetApp
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Label
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import tachiyomi.domain.ui.UiPreferences
import tachiyomi.ui.R
import tachiyomi.ui.core.components.NoElevationOverlay
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.prefs.PreferenceRow
import tachiyomi.ui.core.prefs.SwitchPreference
import tachiyomi.ui.core.theme.CustomColors
import tachiyomi.ui.core.viewmodel.BaseViewModel
import tachiyomi.ui.core.viewmodel.viewModel
import javax.inject.Inject

class MoreViewModel @Inject constructor(
  uiPreferences: UiPreferences
) : BaseViewModel() {

  val downloadedOnly = uiPreferences.downloadedOnly().asState()
  val incognitoMode = uiPreferences.incognitoMode().asState()
}

@Composable
fun MoreScreen(
  openDownloads: () -> Unit,
  openCategories: () -> Unit,
  openSettings: () -> Unit,
  openAbout: () -> Unit
) {
  val vm = viewModel<MoreViewModel>()
  val uriHandler = LocalUriHandler.current

  Column {
    Toolbar(
      title = { Text(stringResource(R.string.more_label)) },
      elevation = 0.dp,
      modifier = Modifier.zIndex(1f)
    )
    CompositionLocalProvider(LocalElevationOverlay provides NoElevationOverlay) {
      Surface(
        color = CustomColors.current.bars,
        contentColor = CustomColors.current.onBars,
        modifier = Modifier
          .fillMaxWidth()
          // To ensure that the elevation shadow is drawn behind the Toolbar
          .zIndex(0f),
        elevation = 4.dp
      ) {
        Icon(
          ImageVector.vectorResource(R.drawable.ic_tachi), modifier = Modifier
            .padding(32.dp)
            .size(56.dp),
          contentDescription = null
        )
      }
    }
    LazyColumn {
      item {
        SwitchPreference(
          preference = vm.downloadedOnly,
          title = R.string.downloaded_only,
          subtitle = R.string.downloaded_only_subtitle,
          icon = Icons.Default.CloudOff,
        )
      }
      item {
        SwitchPreference(
          preference = vm.incognitoMode,
          title = R.string.incognito_mode,
          subtitle = R.string.incognito_mode_subtitle,
          icon = ImageVector.vectorResource(R.drawable.ic_glasses),
        )
      }
      item {
        Divider()
      }
      item {
        PreferenceRow(
          title = R.string.download_queue_label,
          icon = Icons.Outlined.GetApp,
          onClick = openDownloads,
        )
      }
      item {
        PreferenceRow(
          title = R.string.categories_label,
          icon = Icons.Outlined.Label,
          onClick = openCategories,
        )
      }
      item {
        Divider()
      }
      item {
        PreferenceRow(
          title = R.string.settings_label,
          icon = Icons.Outlined.Settings,
          onClick = openSettings,
        )
      }
      item {
        PreferenceRow(
          title = R.string.about_label,
          icon = Icons.Outlined.Info,
          onClick = openAbout,
        )
      }
      item {
        PreferenceRow(
          title = R.string.help_label,
          icon = Icons.Outlined.HelpOutline,
          onClick = { uriHandler.openUri("https://tachiyomi.org/help/") },
        )
      }
    }
  }
}
