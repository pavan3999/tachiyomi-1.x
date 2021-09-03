package tachiyomi.ui.updates.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tachiyomi.ui.R
import tachiyomi.ui.core.coil.rememberMangaCover
import tachiyomi.ui.core.components.MangaListItem
import tachiyomi.ui.core.components.MangaListItemColumn
import tachiyomi.ui.core.components.MangaListItemImage
import tachiyomi.ui.core.components.MangaListItemSubtitle
import tachiyomi.ui.core.components.MangaListItemTitle
import tachiyomi.ui.core.components.selectedBackground
import tachiyomi.domain.updates.model.UpdatesManga as Manga

@Composable
fun UpdatesItem(
  manga: Manga,
  isSelected: Boolean,
  onClickItem: (Manga) -> Unit,
  onLongClickItem: (Manga) -> Unit,
  onClickCover: (Manga) -> Unit,
  onClickDownload: (Manga) -> Unit
) {
  val alpha = if (manga.read) 0.38f else 1f

  MangaListItem(
    modifier = Modifier
      .combinedClickable(
        onClick = { onClickItem(manga) },
        onLongClick = { onLongClickItem(manga) }
      )
      .selectedBackground(isSelected)
      .height(56.dp)
      .fillMaxWidth()
      .padding(end = 4.dp)
  ) {
    MangaListItemImage(
      modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
        .clip(MaterialTheme.shapes.medium)
        .clickable { onClickCover(manga) },
      mangaCover = rememberMangaCover(manga)
    )
    MangaListItemColumn(
      modifier = Modifier
        .weight(1f)
        .padding(start = 16.dp)
        .alpha(alpha)
    ) {
      MangaListItemTitle(
        text = manga.title,
        fontWeight = FontWeight.SemiBold
      )
      MangaListItemSubtitle(
        text = stringResource(R.string.updates_subtitle, manga.number, manga.name)
      )
    }
    // TODO Replace with Download Composable when that is implemented
    IconButton(onClick = { onClickDownload(manga) }) {
      Icon(imageVector = Icons.Outlined.Download, contentDescription = "")
    }
  }
}

private val manga = Manga(
  id = -1,
  sourceId = -1,
  key = "Key",
  title = "Title",
  cover = "",
  favorite = true,
  dateUpload = -1,
  chapterId = -1,
  name = "Name",
  read = false,
  number = 69f,
  date = "2021-08-12"
)

@Preview(showBackground = true)
@Composable
fun UpdatesItemPreview() {
  UpdatesItem(
    manga = manga,
    isSelected = false,
    onClickItem = {},
    onLongClickItem = {},
    onClickCover = {},
    onClickDownload = {}
  )
}

@Preview(showBackground = true)
@Composable
fun UpdatesItemReadPreview() {
  UpdatesItem(
    manga = manga.copy(read = true),
    isSelected = false,
    onClickItem = {},
    onLongClickItem = {},
    onClickCover = {},
    onClickDownload = {}
  )
}

@Preview(showBackground = true)
@Composable
fun UpdatesItemSelectedPreview() {
  UpdatesItem(
    manga = manga,
    isSelected = true,
    onClickItem = {},
    onLongClickItem = {},
    onClickCover = {},
    onClickDownload = {}
  )
}

@Preview(showBackground = true)
@Composable
fun UpdatesItemSelectedAndReadPreview() {
  UpdatesItem(
    manga = manga.copy(read = true),
    isSelected = true,
    onClickItem = {},
    onLongClickItem = {},
    onClickCover = {},
    onClickDownload = {}
  )
}
