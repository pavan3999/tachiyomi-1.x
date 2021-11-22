/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.more.settings

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.platform.LocalContext
import tachiyomi.i18n.MR
import tachiyomi.ui.core.prefs.PreferenceRow

internal actual fun LazyListScope.settingsGeneralManageNotifications() {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    item {
      val context = LocalContext.current
      PreferenceRow(title = MR.strings.manage_notifications, onClick = {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
          putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
        context.startActivity(intent)
      })
    }
  }
}
