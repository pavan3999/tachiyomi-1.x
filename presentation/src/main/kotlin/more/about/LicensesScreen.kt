/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.more.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import tachiyomi.ui.R
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.components.Toolbar

@Composable
fun LicensesScreen(
  navController: NavHostController,
) {
  val libraries = Libs(LocalContext.current).libraries.sortedBy { it.libraryName.lowercase() }

  Scaffold(
    topBar = {
      Toolbar(
        title = { Text(stringResource(R.string.licenses_label)) },
        navigationIcon = { BackIconButton(navController) },
      )
    },
  ) {
    LazyColumn {
      items(libraries) { library ->
        LicenseItem(library)
      }
    }
  }
}

@Composable
private fun LicenseItem(library: Library) {
  val uriHandler = LocalUriHandler.current

  val website = library.libraryWebsite
  val modifier = when {
    website.isEmpty() -> Modifier
    else -> Modifier.clickable { uriHandler.openUri(website) }
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    Text("${library.libraryName} ${library.libraryVersion}")
    CompositionLocalProvider(
      LocalTextStyle provides MaterialTheme.typography.caption,
      LocalContentAlpha provides ContentAlpha.medium,
    ) {
      Text(library.libraryArtifactId)
      library.licenses?.let { licenses ->
        Text(licenses.joinToString { it.licenseName })
      }
    }
  }
}
