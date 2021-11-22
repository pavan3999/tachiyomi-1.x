/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable

@Composable
fun BackIconButton(onClick: () -> Unit) {
  IconButton(onClick = onClick) {
    Icon(Icons.Default.ArrowBack, contentDescription = null)
  }
}

@Composable
fun CloseIconButton(onClick: () -> Unit) {
  IconButton(onClick = onClick) {
    Icon(Icons.Default.Close, contentDescription = null)
  }
}
