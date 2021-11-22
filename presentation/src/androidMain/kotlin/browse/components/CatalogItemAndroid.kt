/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.browse.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import tachiyomi.domain.catalog.model.CatalogRemote

@Preview(showBackground = true)
@Composable
private fun CatalogItemPreview() {
  CatalogItem(
    catalog = CatalogRemote(
      name = "My Catalog",
      description = "Some description",
      sourceId = 0L,
      pkgName = "my.catalog",
      versionName = "1.0.0",
      versionCode = 1,
      lang = "en",
      pkgUrl = "",
      iconUrl = "",
      nsfw = false,
    ),
    onClick = {},
    onInstall = {},
    onUninstall = {},
    onPinToggle = {}
  )
}
