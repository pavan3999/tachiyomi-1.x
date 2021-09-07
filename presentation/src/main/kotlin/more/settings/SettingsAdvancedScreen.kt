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
import androidx.compose.ui.res.stringResource
import tachiyomi.ui.R
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.Toolbar

@Composable
fun SettingsAdvancedScreen(
  navigateUp: () -> Unit
) {
  Column {
    Toolbar(
      title = { Text(stringResource(R.string.advanced_label)) },
      navigationIcon = { BackIconButton(navigateUp) }
    )
    LazyColumn {
    }
  }
}
