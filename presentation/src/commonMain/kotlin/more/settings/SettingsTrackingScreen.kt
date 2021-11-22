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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import tachiyomi.core.di.Inject
import tachiyomi.domain.track.sites.BasicTrackSite
import tachiyomi.domain.track.sites.OAuthTrackSite
import tachiyomi.domain.track.sites.TrackServices
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.prefs.PreferenceRow
import tachiyomi.ui.core.viewmodel.BaseViewModel
import tachiyomi.ui.core.viewmodel.viewModel

class TrackersViewModel @Inject constructor(
  trackServices: TrackServices,
) : BaseViewModel() {

  val trackers = trackServices.trackers
}

@Composable
fun SettingsTrackingScreen(
  navigateUp: () -> Unit
) {
  val vm = viewModel<TrackersViewModel>()
  val uriHandler = LocalUriHandler.current

  Column {
    Toolbar(
      title = { Text(localize(MR.strings.tracking_label)) },
      navigationIcon = { BackIconButton(navigateUp) }
    )
    LazyColumn {
      items(vm.trackers) { tracker ->
        PreferenceRow(title = tracker.name, onClick = {
          when (tracker) {
            is OAuthTrackSite -> {
              uriHandler.openUri(tracker.loginUrl.toString())
            }
            is BasicTrackSite -> {
              // TODO: login dialog
            }
          }
        })
      }
    }
  }
}
