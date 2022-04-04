/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.viewmodel

import androidx.compose.runtime.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import tachiyomi.core.prefs.Preference
import tachiyomi.ui.core.prefs.PreferenceMutableState

expect abstract class BaseViewModel() {

  protected val scope: CoroutineScope

  open fun onDestroy()

  fun <T> Preference<T>.asState(): PreferenceMutableState<T>

  fun <T> Preference<T>.asState(onChange: (T) -> Unit): PreferenceMutableState<T>

  fun <T> Flow<T>.asState(initialValue: T): State<T>

  fun <T> StateFlow<T>.asState(): State<T>

  fun <T> Flow<T>.launchWhileActive(): Job

}
