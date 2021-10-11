/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.download.service

import okio.Path
import okio.Path.Companion.toPath
import tachiyomi.core.prefs.Preference
import tachiyomi.core.prefs.PreferenceStore

class DownloadPreferences(
  private val preferenceStore: PreferenceStore,
  private val defaultDownloadsDir: Path
) {

  fun downloadsDir(): Preference<Path> {
    return preferenceStore.getObject(
      key = "downloads_dir",
      defaultValue = defaultDownloadsDir,
      serializer = { it.toString() },
      deserializer = { it.toPath() }
    )
  }

  fun compress(): Preference<Boolean> {
    return preferenceStore.getBoolean("compress", false)
  }

}
