/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.emoji2.widget.EmojiTextView

@Composable
actual fun EmojiText(
  text: String,
  modifier: Modifier
) {
  AndroidView(
    factory = { EmojiTextView(it, null).apply { setTextColor(Color.BLACK) } },
    modifier = modifier,
    update = { it.text = text }
  )
}
