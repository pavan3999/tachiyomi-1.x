/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import java.util.Properties

internal actual class DatabaseDriverFactory {
  actual fun create(): SqlDriver {
    val driver = JdbcSqliteDriver(
      url = JdbcSqliteDriver.IN_MEMORY + "/tmp/tachiyomi.db",
      properties = Properties().apply {
        put("foreign_keys", "true")
      }
    )
    Database.Schema.create(driver)
    return driver
  }
}
