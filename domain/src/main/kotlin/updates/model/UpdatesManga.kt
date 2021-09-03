/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.updates.model

import tachiyomi.domain.manga.model.MangaBase

data class UpdatesManga(
  override val id: Long,
  override val sourceId: Long,
  override val key: String,
  override val title: String,
  val cover: String,
  val favorite: Boolean,
  val dateUpload: Long,
  val chapterId: Long,
  val name: String,
  val read: Boolean,
  val number: Float,
  val date: String
) : MangaBase
