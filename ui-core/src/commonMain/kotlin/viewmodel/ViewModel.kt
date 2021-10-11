package tachiyomi.ui.core.viewmodel

import androidx.compose.runtime.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import tachiyomi.core.prefs.Preference
import tachiyomi.ui.core.prefs.PreferenceMutableState

expect abstract class BaseViewModel {

  protected val scope: CoroutineScope

  open fun onDestroy()

  fun <T> Preference<T>.asState(): PreferenceMutableState<T>

  fun <T> Flow<T>.asState(initialValue: T): State<T>

  fun <T> StateFlow<T>.asState(): State<T>

  fun <T> Flow<T>.launchWhileActive(): Job

}
