/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.catalog.interactor

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import tachiyomi.domain.catalog.model.CatalogInstalled
import tachiyomi.domain.catalog.model.CatalogRemote

class GetCatalogsByTypeTest : StringSpec({

  val localCatalogs = mockk<GetLocalCatalogs>(relaxed = true)
  val remoteCatalogs = mockk<GetRemoteCatalogs>(relaxed = true)
  val interactor = GetCatalogsByType(localCatalogs, remoteCatalogs)
  afterTest { clearAllMocks() }

  "subscribes to catalogs" {
    coEvery { localCatalogs.subscribe(any()) } returns flowOf()
    interactor.subscribe()
    coVerify { localCatalogs.subscribe(any()) }
    coVerify { remoteCatalogs.subscribe(any()) }
  }
  "returns all catalogs" {
    coEvery { localCatalogs.subscribe(any()) } returns flowOf(
      listOf(
        mockCatalogInstalled("a"),
        mockCatalogInstalled("b")
      )
    )
    coEvery { remoteCatalogs.subscribe() } returns flowOf(
      listOf(
        mockCatalogRemote("a"),
        mockCatalogRemote("c")
      )
    )

    val (_, unpinned, remote) = interactor.subscribe(excludeRemoteInstalled = false).first()
    unpinned shouldHaveSize 2
    remote shouldHaveSize 2
  }
  "filters remote installed" {
    coEvery { localCatalogs.subscribe(any()) } returns flowOf(
      listOf(
        mockCatalogInstalled("a"),
        mockCatalogInstalled("b")
      )
    )
    coEvery { remoteCatalogs.subscribe() } returns flowOf(
      listOf(
        mockCatalogRemote("a"),
        mockCatalogRemote("c")
      )
    )

    val (_, unpinned, remote) = interactor.subscribe(excludeRemoteInstalled = true).first()
    unpinned shouldHaveSize 2
    remote shouldHaveSize 1
  }
  "splits pinned and unpinned catalogs" {
    coEvery { localCatalogs.subscribe(any()) } returns flowOf(
      listOf(
        mockCatalogInstalled("a"),
        mockk<CatalogInstalled> {
          every { isPinned } returns true
          every { hasUpdate } returns false
        }
      )
    )
    coEvery { remoteCatalogs.subscribe() } returns flowOf(emptyList())

    val (pinned, unpinned, _) = interactor.subscribe().first()
    pinned shouldHaveSize 1
    unpinned shouldHaveSize 1
  }
  "sorts catalogs by hasUpdate field first" {
    coEvery { localCatalogs.subscribe(any()) } returns flowOf(
      listOf(
        mockk<CatalogInstalled> {
          every { name } returns "C"
          every { isPinned } returns false
          every { hasUpdate } returns false
        },
        mockk<CatalogInstalled> {
          every { name } returns "B"
          every { isPinned } returns false
          every { hasUpdate } returns true
        },
        mockk<CatalogInstalled> {
          every { name } returns "A"
          every { isPinned } returns false
          every { hasUpdate } returns false
        },
        mockk<CatalogInstalled> {
          every { name } returns "A"
          every { isPinned } returns true
          every { hasUpdate } returns false
        },
        mockk<CatalogInstalled> {
          every { name } returns "B"
          every { isPinned } returns true
          every { hasUpdate } returns true
        },
        mockk<CatalogInstalled> {
          every { name } returns "C"
          every { isPinned } returns true
          every { hasUpdate } returns false
        }
      )
    )
    coEvery { remoteCatalogs.subscribe() } returns flowOf(emptyList())

    val (pinned, unpinned, _) = interactor.subscribe().first()

    unpinned[0].name shouldBe "B"
    unpinned[1].name shouldBe "C"
    unpinned[2].name shouldBe "A"
    pinned[0].name shouldBe "B"
    pinned[1].name shouldBe "A"
    pinned[2].name shouldBe "C"
  }

})

private fun mockCatalogInstalled(mockPkgName: String): CatalogInstalled {
  return mockk {
    every { pkgName } returns mockPkgName
    every { isPinned } returns false
    every { hasUpdate } returns false
  }
}

private fun mockCatalogRemote(mockPkgName: String): CatalogRemote {
  return mockk {
    every { pkgName } returns mockPkgName
  }
}
