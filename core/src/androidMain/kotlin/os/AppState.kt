/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.core.os

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tachiyomi.core.log.Log

@Suppress("ObjectPropertyName")
actual object AppState : DefaultLifecycleObserver {

  private val _networkFlow = MutableStateFlow(false)
  actual val networkFlow: StateFlow<Boolean> get() = _networkFlow

  private val _foregroundFlow = MutableStateFlow(false)
  actual val foregroundFlow: StateFlow<Boolean> get() = _foregroundFlow

  init {
    ProcessLifecycleOwner.get().lifecycle.addObserver(this)
  }

  override fun onStart(owner: LifecycleOwner) {
    Log.debug("Application now in foreground")
    _foregroundFlow.value = true
  }

  override fun onStop(owner: LifecycleOwner) {
    Log.debug("Application went to background")
    _foregroundFlow.value = false
  }

}
