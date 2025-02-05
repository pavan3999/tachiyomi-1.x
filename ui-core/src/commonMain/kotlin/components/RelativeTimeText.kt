/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDate
import tachiyomi.core.util.asRelativeTimeString

@Composable
fun RelativeTimeText(modifier: Modifier = Modifier, date: LocalDate) {
  Text(
    text = date.asRelativeTimeString(),
    modifier = modifier,
    color = MaterialTheme.colors.onBackground
  )
}
