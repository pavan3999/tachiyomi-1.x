package tachiyomi.ui.core.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tachiyomi.ui.core.util.getRelativeTimeString
import java.util.Date

@Composable
fun RelativeTimeText(modifier: Modifier = Modifier, date: Date) {
  Text(
    text = date.getRelativeTimeString(),
    modifier = modifier,
    color = MaterialTheme.colors.onBackground
  )
}
