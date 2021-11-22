/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.i18n

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

expect object Images {

  @Composable
  fun discord(): ImageVector

  @Composable
  fun facebook(): ImageVector

  @Composable
  fun github(): ImageVector

  @Composable
  fun glasses(): ImageVector

  @Composable
  fun reddit(): ImageVector

  @Composable
  fun tachi(): ImageVector

  @Composable
  fun twitter(): ImageVector
}
