/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.DoneOutline
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Label
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tachiyomi.ui.core.theme.AppColors

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LibrarySelectionBar(
  visible: Boolean,
  onClickChangeCategory: () -> Unit,
  onClickDownload: () -> Unit,
  onClickMarkAsRead: () -> Unit,
  onClickMarkAsUnread: () -> Unit,
  onClickDeleteDownloads: () -> Unit,
  modifier: Modifier = Modifier
) {
  AnimatedVisibility(
    visible = visible,
    modifier = modifier,
    enter = expandVertically(),
    exit = shrinkVertically()
  ) {
    Surface(
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .padding(bottom = 32.dp)
        .fillMaxWidth(),
      shape = MaterialTheme.shapes.medium,
      color = AppColors.current.bars,
      contentColor = AppColors.current.onBars,
      elevation = 4.dp
    ) {
      Row(
        modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.SpaceAround
      ) {
        IconButton(onClick = onClickChangeCategory) {
          Icon(Outlined.Label, contentDescription = null)
        }
        IconButton(onClick = onClickDownload) {
          Icon(Outlined.Download, contentDescription = null)
        }
        IconButton(onClick = onClickMarkAsRead) {
          Icon(Outlined.Done, contentDescription = null)
        }
        IconButton(onClick = onClickMarkAsUnread) {
          Icon(Outlined.DoneOutline, contentDescription = null)
        }
        IconButton(onClick = onClickDeleteDownloads) {
          Icon(Outlined.Delete, contentDescription = null)
        }
      }
    }
  }
}
