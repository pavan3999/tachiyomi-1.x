/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.updates.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import tachiyomi.domain.updates.model.UpdatesManga

private val manga
  get() = UpdatesManga(
    id = -1,
    sourceId = -1,
    key = "Key",
    title = "Title",
    cover = "",
    favorite = true,
    dateUpload = -1,
    chapterId = -1,
    name = "Name",
    read = false,
    number = 69f,
    date = "2021-08-12"
  )

@Preview(showBackground = true)
@Composable
fun UpdatesItemPreview() {
  UpdatesItem(
    manga = manga,
    isSelected = false,
    onClickItem = {},
    onLongClickItem = {},
    onClickCover = {},
    onClickDownload = {}
  )
}

@Preview(showBackground = true)
@Composable
fun UpdatesItemReadPreview() {
  UpdatesItem(
    manga = manga.copy(read = true),
    isSelected = false,
    onClickItem = {},
    onLongClickItem = {},
    onClickCover = {},
    onClickDownload = {}
  )
}

@Preview(showBackground = true)
@Composable
fun UpdatesItemSelectedPreview() {
  UpdatesItem(
    manga = manga,
    isSelected = true,
    onClickItem = {},
    onLongClickItem = {},
    onClickCover = {},
    onClickDownload = {}
  )
}

@Preview(showBackground = true)
@Composable
fun UpdatesItemSelectedAndReadPreview() {
  UpdatesItem(
    manga = manga.copy(read = true),
    isSelected = true,
    onClickItem = {},
    onLongClickItem = {},
    onClickCover = {},
    onClickDownload = {}
  )
}
