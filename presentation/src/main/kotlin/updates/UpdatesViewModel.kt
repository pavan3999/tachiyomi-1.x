package tachiyomi.ui.updates

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tachiyomi.domain.updates.interactor.GetUpdatesGroupByDate
import tachiyomi.ui.core.viewmodel.BaseViewModel
import java.util.Date
import javax.inject.Inject
import tachiyomi.domain.updates.model.UpdatesManga as Manga

typealias UpdateMangaByDate = Map.Entry<Date, List<Manga>>

class UpdatesViewModel @Inject constructor(
  private val state: UpdatesStateImpl,
  getUpdatesGroupByDate: GetUpdatesGroupByDate
) : BaseViewModel(), UpdatesState by state {

  init {
    scope.launch {
      getUpdatesGroupByDate
        .subscribeAll()
        .collect {
          state.updates = it
        }
    }
  }

  fun unselectAll() {
    state.selection.clear()
  }

  fun selectAll() {
    val allChapterIds = state.updates
      .flatMap(UpdateMangaByDate::value)
      .map(Manga::chapterId)
    state.selection.clear()
    state.selection.addAll(allChapterIds)
  }

  fun flipSelection() {
    val notSelectedIds = state.updates
      .flatMap(UpdateMangaByDate::value)
      .map(Manga::chapterId)
      .filterNot { id -> id in state.selection }
    state.selection.clear()
    state.selection.addAll(notSelectedIds)
  }

  fun updateLibrary() {
    // TODO
  }

  fun toggleManga(manga: Manga) {
    when (val id = manga.chapterId) {
      in state.selection -> state.selection.remove(id)
      else -> state.selection.add(id)
    }
  }
}