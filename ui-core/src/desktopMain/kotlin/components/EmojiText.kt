/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// TODO(inorichi): emojis do not work on desktop, this is just a dummy implementation
@Composable
actual fun EmojiText(
  text: String,
  modifier: Modifier
) {
  Text(text, modifier)
}
