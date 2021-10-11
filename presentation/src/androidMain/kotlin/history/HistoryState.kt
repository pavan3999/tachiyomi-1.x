/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.history

import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.setValue
import kotlinx.datetime.LocalDate
import tachiyomi.domain.history.model.HistoryWithRelations as History

@Stable
interface HistoryState {

  val isLoading: Boolean
  val isEmpty: Boolean
  val history: Map<LocalDate, List<History>>
  val isSearching: Boolean
  var query: String?
}

fun HistoryState(): HistoryState {
  return HistoryStateImpl()
}

class HistoryStateImpl : HistoryState {

  override var isLoading: Boolean by mutableStateOf(true)
  override val isEmpty: Boolean by derivedStateOf { history.isEmpty() }
  override var history: Map<LocalDate, List<History>> by mutableStateOf(emptyMap())
  override val isSearching: Boolean by derivedStateOf { query != null }
  override var query: String? by mutableStateOf(null)

  var allHistory by mutableStateOf(
    mapOf<LocalDate, List<History>>(),
    referentialEqualityPolicy()
  )
}
