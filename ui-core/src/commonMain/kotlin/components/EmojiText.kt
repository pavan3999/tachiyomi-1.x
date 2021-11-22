/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// TODO(inorichi): this is a workaround until emojis are compatible with compose. Text color and
//  size is unsupported.
@Composable
expect fun EmojiText(
  text: String,
  modifier: Modifier = Modifier
)
