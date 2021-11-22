/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.more.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.icerock.moko.resources.StringResource
import tachiyomi.i18n.Images
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.NoElevationOverlay
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.theme.AppColors

@Composable
fun LogoHeaderScaffold(
  titleResId: StringResource,
  navigationIcon: @Composable (() -> Unit)? = null,
  content: @Composable () -> Unit,
) {
  Column {
    Toolbar(
      title = { Text(localize(titleResId)) },
      elevation = 0.dp,
      modifier = Modifier.zIndex(1f),
      navigationIcon = navigationIcon,
    )
    CompositionLocalProvider(LocalElevationOverlay provides NoElevationOverlay) {
      Surface(
        color = AppColors.current.bars,
        contentColor = AppColors.current.onBars,
        modifier = Modifier
          .fillMaxWidth()
          // To ensure that the elevation shadow is drawn behind the Toolbar
          .zIndex(0f),
        elevation = 4.dp
      ) {
        Icon(
          imageVector = Images.tachi(),
          contentDescription = null,
          modifier = Modifier
            .padding(32.dp)
            .size(56.dp)
        )
      }
    }
    content()
  }
}
