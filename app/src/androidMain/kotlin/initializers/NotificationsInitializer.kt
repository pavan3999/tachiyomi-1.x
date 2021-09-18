/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.app.initializers

import android.app.Application
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import tachiyomi.app.R
import tachiyomi.ui.library.LibraryNotifier
import javax.inject.Inject

class NotificationsInitializer @Inject constructor(
  context: Application,
  notificationManager: NotificationManagerCompat,
  libraryNotifier: LibraryNotifier
) {

  init {
    // TODO either inject channels here or write initialization in the data & core packages
    val libraryChannel =
      NotificationChannelCompat.Builder("library", NotificationManagerCompat.IMPORTANCE_LOW)
        .setName(context.getString(R.string.notif_channel_library))
        .build()
    val channelList = listOf(libraryChannel)
    notificationManager.createNotificationChannelsCompat(channelList)

    libraryNotifier.init()
  }

}
