/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.components

import androidx.compose.runtime.Composable

// TODO(inorichi): default parameters are not working on kotlin 1.5.31:
//   https://github.com/JetBrains/compose-jb/issues/758
@Composable
internal expect fun PlatformBackHandler(enabled: Boolean, onBack: () -> Unit)

@Composable
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
  PlatformBackHandler(enabled, onBack)
}
