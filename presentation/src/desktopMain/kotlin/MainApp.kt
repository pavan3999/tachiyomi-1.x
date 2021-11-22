/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.theme.AppColors
import tachiyomi.ui.core.theme.themes

@Composable
fun MainApp() {
  val theme = themes.last()
  AppColors(theme.materialColors, theme.extraColors) {
    Surface {
      Toolbar(
        title = { Text(localize(MR.strings.app_name)) }
      )
      Box(Modifier.fillMaxSize())
    }
  }
}
