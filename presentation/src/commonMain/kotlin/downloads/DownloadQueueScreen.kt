/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.downloads

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.Toolbar

@Composable
fun DownloadQueueScreen(
  navigateUp: () -> Unit
) {
  Scaffold(
    topBar = {
      Toolbar(
        title = { Text(localize(MR.strings.download_queue_label)) },
        navigationIcon = { BackIconButton(navigateUp) },
      )
    }
  ) {
  }
}
