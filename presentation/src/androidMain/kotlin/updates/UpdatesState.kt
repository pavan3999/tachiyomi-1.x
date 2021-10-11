package tachiyomi.ui.updates

import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.datetime.LocalDate
import tachiyomi.domain.updates.model.UpdatesManga

@Stable
interface UpdatesState {

  val isLoading: Boolean
  val isEmpty: Boolean
  val updates: Map<LocalDate, List<UpdatesManga>>
  val selection: List<Long>
  val hasSelection: Boolean
}

fun UpdatesState(): UpdatesState {
  return UpdatesStateImpl()
}

class UpdatesStateImpl : UpdatesState {

  override var isLoading: Boolean by mutableStateOf(true)
  override val isEmpty: Boolean by derivedStateOf { updates.isEmpty() }
  override var updates: Map<LocalDate, List<UpdatesManga>> by mutableStateOf(emptyMap())
  override val selection: SnapshotStateList<Long> = mutableStateListOf()
  override val hasSelection: Boolean by derivedStateOf { selection.isNotEmpty() }
}
