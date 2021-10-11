/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.library.service

import okio.FileSystem
import okio.Path
import tachiyomi.core.io.setLastModified

class LibraryCovers(
  private val fileSystem: FileSystem,
  private val path: Path
) {

  init {
    fileSystem.createDirectories(path)
  }

  fun find(mangaId: Long): Path {
    return path / "$mangaId.0"
  }

  fun delete(mangaId: Long) {
    return fileSystem.delete(find(mangaId))
  }

  fun invalidate(mangaId: Long) {
    find(mangaId).setLastModified(0)
  }

}
