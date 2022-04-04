/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.collect
import tachiyomi.domain.library.model.Category

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LibrarySheetLayout(
  showSheet: Boolean,
  currentPage: Int,
  currentCategory: Category?,
  onSheetDismissed: () -> Unit,
  onPageChanged: (Int) -> Unit,
  content: @Composable () -> Unit
) {
  val sheetState = remember { ModalBottomSheetState(Hidden) }

  // Check whether the sheet needs to be shown or hidden.
  LaunchedEffect(showSheet) {
    if (showSheet) {
      sheetState.show()
    } else if (sheetState.isVisible) {
      sheetState.hide()
    }
  }
  // Notify when the sheet has been dismissed
  LaunchedEffect(sheetState) {
    snapshotFlow { !sheetState.isVisible }
      .collect { hide -> if (hide) onSheetDismissed() }
  }

  ModalBottomSheetLayout(
    sheetState = sheetState,
    sheetContent = { LibrarySheet(currentPage, currentCategory, onPageChanged) },
    content = content
  )
}
