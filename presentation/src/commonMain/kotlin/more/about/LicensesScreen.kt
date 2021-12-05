/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.more.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.Toolbar

@Composable
fun LicensesScreen(
  navigateUp: () -> Unit
) {
  Scaffold(
    topBar = {
      Toolbar(
        title = { Text(localize(MR.strings.licenses_label)) },
        navigationIcon = { BackIconButton(navigateUp) },
      )
    },
  ) {
    LicensesContent(modifier = Modifier.fillMaxSize())
  }
}

@Composable
expect fun LicensesContent(modifier: Modifier)
