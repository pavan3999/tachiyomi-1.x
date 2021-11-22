/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.StringResource
import tachiyomi.i18n.localize

private val kaomojis = listOf(
  "(･o･;)",
  "Σ(ಠ_ಠ)",
  "ಥ_ಥ",
  "(˘･_･˘)",
  "(；￣Д￣)",
  "(･Д･。"
)

@Composable
fun EmptyScreen(
  text: StringResource
) {
  val kaomoji = remember { kaomojis.random() }

  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = kaomoji,
      style = MaterialTheme.typography.body2.copy(
        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
        fontSize = 48.sp
      ),
    )
    Text(
      text = localize(text),
      style = MaterialTheme.typography.body2.copy(
        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
      ),
      textAlign = TextAlign.Center,
      modifier = Modifier
        .padding(top = 16.dp)
        .padding(horizontal = 16.dp)
    )
  }
}
