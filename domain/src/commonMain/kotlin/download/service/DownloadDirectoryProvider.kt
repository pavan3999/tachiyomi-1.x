/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.download.service

import okio.Path
import tachiyomi.core.di.Inject
import tachiyomi.core.di.Singleton
import tachiyomi.domain.download.model.QueuedDownload

/**
 * This class is used to provide the directories where the downloads should be saved.
 * It uses the following path scheme: /<root downloads dir>/<source name>/<manga>/<chapter>
 */
@Singleton
class DownloadDirectoryProvider @Inject internal constructor(
  private val preferences: DownloadPreferences
) {

  val downloadsDir = preferences.downloadsDir().get() // TODO update value

  fun getChapterDir(download: QueuedDownload): Path {
    TODO()
    //return File()
  }

  internal fun getTempChapterDir(download: QueuedDownload): Path {
    TODO()
    //return File("_tmp")
  }

}
