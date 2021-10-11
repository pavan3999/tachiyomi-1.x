/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.core.io

import okio.FileSystem
import okio.Path
import okio.Source

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect fun FileSystem.createZip(
  destination: Path,
  compress: Boolean,
  block: ZipWriterScope.() -> Unit
)

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect class ZipWriterScope {
  fun addFile(destination: String, source: Source)
  fun addDirectory(destination: String)
}
