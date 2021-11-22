/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.browse.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tachiyomi.ui.browse.LanguageChoice
import tachiyomi.ui.browse.LanguageChoice.*

@Composable
fun LanguageChipGroup(
  choices: List<LanguageChoice>,
  selected: LanguageChoice?,
  onClick: (LanguageChoice) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyRow(modifier = modifier) {
    items(
      items = choices,
      key = { choice ->
        when (choice) {
          All -> "all"
          is One -> choice.language.code
          is Others -> "others"
        }
      }
    ) { choice ->
      LanguageChip(
        choice = choice,
        isSelected = choice == selected,
        onClick = { onClick(choice) }
      )
    }
  }
}
