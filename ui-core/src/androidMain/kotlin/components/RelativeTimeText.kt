package tachiyomi.ui.core.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDate
import tachiyomi.core.util.asRelativeTimeString

@Composable
fun RelativeTimeText(modifier: Modifier = Modifier, date: LocalDate) {
  Text(
    text = date.asRelativeTimeString(),
    modifier = modifier,
    color = MaterialTheme.colors.onBackground
  )
}
