/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

@file:Suppress("PackageDirectoryMismatch")

package tachiyomi.domain.history.model

import androidx.room.Entity

@Entity(primaryKeys = ["mangaId", "chapterId"])
data class History(
  val readAt: Long,
  val mangaId: Long,
  val chapterId: Long
)
