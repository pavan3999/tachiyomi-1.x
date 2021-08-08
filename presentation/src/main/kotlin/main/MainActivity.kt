/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import tachiyomi.core.di.AppScope
import tachiyomi.domain.ui.UiPreferences
import tachiyomi.domain.ui.model.StartScreen
import tachiyomi.ui.core.activity.BaseActivity
import tachiyomi.ui.core.theme.AppTheme

class MainActivity : BaseActivity() {

  private val uiPrefs = AppScope.getInstance<UiPreferences>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val startRoute = uiPrefs.startScreen().get().toRoute()

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      AppTheme {
        MainNavHost(startRoute)
        ConfirmExitBackHandler(uiPrefs)
      }
    }
  }
}

private fun StartScreen.toRoute(): Route {
  return when (this) {
    StartScreen.Library -> Route.Library
    StartScreen.Updates -> Route.Updates
    StartScreen.History -> Route.History
    StartScreen.Browse -> Route.Browse
    StartScreen.More -> Route.More
  }
}
