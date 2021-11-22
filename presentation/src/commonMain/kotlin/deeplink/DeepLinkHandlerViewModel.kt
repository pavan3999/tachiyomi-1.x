/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.deeplink

import tachiyomi.core.di.Inject
import tachiyomi.core.log.Log
import tachiyomi.domain.catalog.model.CatalogInstalled
import tachiyomi.domain.catalog.service.CatalogStore
import tachiyomi.source.DeepLinkSource
import tachiyomi.source.Source
import tachiyomi.source.model.DeepLink
import tachiyomi.ui.core.viewmodel.BaseViewModel

class DeepLinkHandlerViewModel @Inject constructor(
  private val store: CatalogStore,
) : BaseViewModel() {

  fun getDeepLinkResult(referrer: String, url: String): SourceHandler? {
    // Get caller extension
    if (referrer.isEmpty()) {
      Log.warn { "Received an intent from an extension without a receiver" }
      return null
    }

    // Find caller extension
    val catalog = store.catalogs
      .filterIsInstance<CatalogInstalled>()
      .find { it.pkgName == referrer }
    if (catalog == null) {
      Log.warn { "Extension not found: $referrer" }
      return null
    }

    val source = catalog.source
    val sourceHandler = if (source is DeepLinkSource) {
      source.handleLink(url)?.let { SourceHandler(source, it) }
    } else {
      null
    }

    if (sourceHandler == null) {
      Log.warn { "No source could handle link $url" }
      return null
    }

    return sourceHandler
  }
}

data class SourceHandler(val source: Source, val link: DeepLink)
