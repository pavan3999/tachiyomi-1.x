/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.webview

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import tachiyomi.ui.core.components.CloseIconButton
import tachiyomi.ui.core.components.Toolbar

@Composable
fun WebViewScreen(
  navController: NavHostController,
  sourceId: Long,
  url: String,
) {
  val title by remember { mutableStateOf(url) }
  val context = LocalContext.current

  val webview = remember {
    WebView(context).apply {
      setDefaultSettings()

      // TODO: webViewClient (update title, use source headers)
      loadUrl(url)
    }
  }

  DisposableEffect(Unit) {
    onDispose {
      webview.destroy()
    }
  }

  Scaffold(
    topBar = {
      // TODO: browser actions, swipe to refresh
      Toolbar(
        title = { Text(title) },
        navigationIcon = { CloseIconButton(navController) },
      )
    }
  ) { contentPadding ->
    AndroidView(
      factory = { webview },
      modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding)
    )
  }
}
