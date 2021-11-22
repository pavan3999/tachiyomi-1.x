/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tachiyomi.core.prefs.Preference
import tachiyomi.ui.core.prefs.PreferenceMutableState

actual abstract class BaseViewModel {

  protected actual val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

  private val activeScope = MutableStateFlow<CoroutineScope?>(null)

  actual open fun onDestroy() {
  }

  actual fun <T> Preference<T>.asState() = PreferenceMutableState(this, scope)

  actual fun <T> Flow<T>.asState(initialValue: T): State<T> {
    val state = mutableStateOf(initialValue)
    scope.launch {
      collect { state.value = it }
    }
    return state
  }

  actual fun <T> StateFlow<T>.asState(): State<T> {
    val state = mutableStateOf(value)
    scope.launch {
      collect { state.value = it }
    }
    return state
  }

  actual fun <T> Flow<T>.launchWhileActive(): Job {
    return activeScope
      .filterNotNull()
      .onEach { launchIn(it) }
      .launchIn(scope)
  }

}
