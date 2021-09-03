/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.webview

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import tachiyomi.ui.BuildConfig

@SuppressLint("SetJavaScriptEnabled")
fun WebView.setDefaultSettings() {
  // Debug mode (chrome://inspect/#devices)
  if (BuildConfig.DEBUG && 0 != context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
    WebView.setWebContentsDebuggingEnabled(true)
  }

  with(settings) {
    if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
      WebSettingsCompat.setForceDark(this, WebSettingsCompat.FORCE_DARK_AUTO)
    }

    javaScriptEnabled = true
    domStorageEnabled = true
    databaseEnabled = true
    setAppCacheEnabled(true)
    useWideViewPort = true
    loadWithOverviewMode = true
    cacheMode = WebSettings.LOAD_DEFAULT
  }
}
