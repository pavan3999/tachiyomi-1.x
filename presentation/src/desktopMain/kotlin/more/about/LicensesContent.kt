/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.more.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.useResource
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer

/**
 * Renders the licenses for the desktop app.
 *
 * Note that this content does not update automatically (yet), so every once in a while this command
 * should be invoked:
 *   ./gradlew presentation:exportLibraryDefinitions -PexportPath=src/desktopMain/resources/
 */
@Composable
actual fun LicensesContent(modifier: Modifier) {
  val json = useResource("aboutlibraries.json") {
    it.bufferedReader().readText()
  }
  LibrariesContainer(aboutLibsJson = json, modifier = modifier)
}
