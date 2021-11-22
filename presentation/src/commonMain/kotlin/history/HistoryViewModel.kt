/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.history

import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tachiyomi.core.di.Inject
import tachiyomi.domain.history.interactor.DeleteAllHistory
import tachiyomi.domain.history.interactor.DeleteHistory
import tachiyomi.domain.history.interactor.GetHistoryByDate
import tachiyomi.domain.history.model.HistoryWithRelations
import tachiyomi.ui.core.viewmodel.BaseViewModel

class HistoryViewModel @Inject constructor(
  private val state: HistoryStateImpl,
  private val getHistoryByDate: GetHistoryByDate,
  private val deleteHistory: DeleteHistory,
  private val deleteAllHistory: DeleteAllHistory
) : BaseViewModel(), HistoryState by state {

  init {
    scope.launch {
      getHistoryByDate.subscribeAll()
        .collectLatest {
          state.isLoading = false
          state.allHistory = it
        }
    }
    snapshotFlow {
      state.allHistory
        .mapValues { it.value.filteredByQuery(state.query) }
        .filterValues { it.isNotEmpty() }
    }
      .onEach {
        state.history = it
      }
      .launchIn(scope)
  }

  fun delete(history: HistoryWithRelations) {
    state.query = null
    scope.launch {
      deleteHistory.await(history)
    }
  }

  fun deleteAll() {
    state.query = null
    scope.launch {
      deleteAllHistory.await()
    }
  }
}

private fun List<HistoryWithRelations>.filteredByQuery(query: String?): List<HistoryWithRelations> {
  return if (query == null) {
    this
  } else {
    filter { it.mangaTitle.contains(query, true) }
  }
}
