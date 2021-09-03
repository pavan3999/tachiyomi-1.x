package tachiyomi.ui.updates.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import tachiyomi.ui.core.components.RelativeTimeText
import tachiyomi.ui.updates.UpdatesState
import tachiyomi.domain.updates.model.UpdatesManga as Manga

@Composable
fun UpdatesContent(
  state: UpdatesState,
  onClickItem: (Manga) -> Unit,
  onLongClickItem: (Manga) -> Unit,
  onClickCover: (Manga) -> Unit,
  onClickDownload: (Manga) -> Unit
) {
  LazyColumn(
    contentPadding = rememberInsetsPaddingValues(
      insets = LocalWindowInsets.current.navigationBars,
      additionalBottom = 16.dp,
      additionalTop = 8.dp
    )
  ) {
    state.updates.forEach { (date, updates) ->
      stickyHeader {
        RelativeTimeText(
          date = date,
          modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
        )
      }

      items(updates) { manga ->
        UpdatesItem(
          manga = manga,
          isSelected = manga.chapterId in state.selection,
          onClickItem = onClickItem,
          onLongClickItem = onLongClickItem,
          onClickCover = onClickCover,
          onClickDownload = onClickDownload
        )
      }
    }
  }
}
