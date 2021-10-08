/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data.catalog

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.flow.MutableSharedFlow
import tachiyomi.domain.catalog.service.CatalogInstallationChange
import tachiyomi.domain.catalog.service.CatalogInstallationChange.*
import tachiyomi.domain.catalog.service.CatalogInstallationChanges

internal class AndroidCatalogInstallationChanges(
  context: Application
) : CatalogInstallationChanges {

  override val flow = MutableSharedFlow<CatalogInstallationChange>(
    extraBufferCapacity = Int.MAX_VALUE
  )

  init {
    val filter = IntentFilter().apply {
      addAction(Intent.ACTION_PACKAGE_ADDED)
      addAction(Intent.ACTION_PACKAGE_REMOVED)
      addDataScheme("package")
    }
    context.registerReceiver(Receiver(), filter)
  }

  fun notifyAppInstall(pkgName: String) {
    flow.tryEmit(LocalInstall(pkgName))
  }

  fun notifyAppUninstall(pkgName: String) {
    flow.tryEmit(LocalUninstall(pkgName))
  }

  private inner class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      if (intent == null) return

      val pkgName = intent.data?.encodedSchemeSpecificPart ?: return

      when (intent.action) {
        Intent.ACTION_PACKAGE_ADDED -> {
          flow.tryEmit(SystemInstall(pkgName))
        }
        Intent.ACTION_PACKAGE_REMOVED -> {
          flow.tryEmit(SystemUninstall(pkgName))
        }
      }
    }
  }

}
