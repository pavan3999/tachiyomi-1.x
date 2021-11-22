/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.deeplink

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import tachiyomi.source.model.DeepLink
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.Toolbar
import tachiyomi.ui.core.viewmodel.viewModel

@Composable
fun DeepLinkHandlerScreen(
  referrer: String,
  url: String,
  navigateUp: () -> Unit
) {
  val vm = viewModel<DeepLinkHandlerViewModel>()

  val deeplink = vm.getDeepLinkResult(referrer, url)
  if (deeplink == null) {
    LaunchedEffect(Unit) {
      navigateUp()
    }
    return
  }

  // TODO: actually resolve manga or chapter and navigate there

  Column {
    Toolbar(
      title = {},
      navigationIcon = { BackIconButton(navigateUp) }
    )
    Text("Source: ${deeplink.source.id}")

    when (deeplink.link) {
      is DeepLink.Manga -> {
        Text("mangaKey: ${deeplink.link.key}")
      }
      is DeepLink.Chapter -> {
        Text("chapterKey: ${deeplink.link.key}")
      }
    }
  }
}
