/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.app

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.flow.collect
import tachiyomi.i18n.localize
import tachiyomi.i18n.MR
import tachiyomi.ui.MainApp
import tachiyomi.ui.core.providers.LocalWindow
import kotlin.system.exitProcess

fun main() = application {
  val state = rememberWindowState()
  Window(
    onCloseRequest = { exitProcess(0) },
    title = localize(MR.strings.app_name),
    state = state
  ) {
    val window = remember { tachiyomi.ui.core.providers.Window() }
    LaunchedEffect(state) {
      snapshotFlow { state.size }
        .collect { window.setSize(it.width, it.height) }
    }
    CompositionLocalProvider(LocalWindow provides window) {
      MainApp()
    }
  }
}
