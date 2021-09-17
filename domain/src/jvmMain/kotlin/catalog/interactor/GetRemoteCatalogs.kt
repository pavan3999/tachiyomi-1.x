/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.catalog.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tachiyomi.domain.catalog.model.CatalogRemote
import tachiyomi.domain.catalog.service.CatalogRemoteRepository
import javax.inject.Inject

class GetRemoteCatalogs @Inject internal constructor(
  private val catalogRemoteRepository: CatalogRemoteRepository
) {

  suspend fun await(): List<CatalogRemote> {
    return catalogRemoteRepository.getRemoteCatalogs()
  }

  fun subscribe(
    withNsfw: Boolean = true
  ): Flow<List<CatalogRemote>> {
    return catalogRemoteRepository.getRemoteCatalogsFlow()
      .map { catalogs ->
        if (withNsfw) {
          catalogs
        } else {
          catalogs.filter { !it.nsfw }
        }
      }
  }

}
