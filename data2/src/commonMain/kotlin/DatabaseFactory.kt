/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data

import com.squareup.sqldelight.db.SqlDriver
import tachiyomi.data.manga.mangaGenresConverter

expect class DatabaseDriverFactory {
  fun create(): SqlDriver
}

fun createDatabase(factory: DatabaseDriverFactory): Database {
  val driver = factory.create()
  return Database(
    driver = driver,
    mangaAdapter = Manga.Adapter(
      genresAdapter = mangaGenresConverter
    )
  )
}