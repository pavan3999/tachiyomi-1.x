/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.browse.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.browse.LanguageChoice
import tachiyomi.ui.browse.LanguageChoice.*
import tachiyomi.ui.core.components.EmojiText

@Composable
fun LanguageChip(choice: LanguageChoice, isSelected: Boolean, onClick: () -> Unit) {
  Surface(
    color = if (isSelected) {
      MaterialTheme.colors.primary
    } else {
      MaterialTheme.colors.onSurface.copy(alpha = 0.25f)
    },
    modifier = Modifier
      .widthIn(min = 56.dp)
      .requiredHeight(40.dp)
      .padding(4.dp)
      .clip(RoundedCornerShape(16.dp))
      .clickable(onClick = onClick)
  ) {
    val text = when (choice) {
      All -> localize(MR.strings.lang_all)
      is One -> choice.language.toEmoji() ?: ""
      is Others -> localize(MR.strings.lang_others)
    }
    if (choice is One) {
      EmojiText(
        text = text,
        modifier = Modifier.wrapContentSize(Alignment.Center)
      )
    } else {
      Text(
        text = text,
        modifier = Modifier.wrapContentSize(Alignment.Center),
        color = if (isSelected) {
          MaterialTheme.colors.onPrimary
        } else {
          Color.Black
        }
      )
    }
  }
}
