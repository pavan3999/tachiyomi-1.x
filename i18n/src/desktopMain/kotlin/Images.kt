/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.res.useResource
import org.xml.sax.InputSource

actual object Images {

  @Composable
  actual fun discord() = rememberVectorXmlResource("drawable/ic_discord.xml")

  @Composable
  actual fun facebook() = rememberVectorXmlResource("drawable/ic_facebook.xml")

  @Composable
  actual fun github() = rememberVectorXmlResource("drawable/ic_github.xml")

  @Composable
  actual fun glasses() = rememberVectorXmlResource("drawable/ic_glasses.xml")

  @Composable
  actual fun reddit() = rememberVectorXmlResource("drawable/ic_reddit.xml")

  @Composable
  actual fun tachi() = rememberVectorXmlResource("drawable/ic_tachi.xml")

  @Composable
  actual fun twitter() = rememberVectorXmlResource("drawable/ic_twitter.xml")

  @Composable
  private fun rememberVectorXmlResource(resourcePath: String): ImageVector {
    val density = LocalDensity.current
    return remember(resourcePath, density) {
      useResource(resourcePath) {
        loadXmlImageVector(InputSource(it), density)
      }
    }
  }
}
