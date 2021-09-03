package tachiyomi.ui.core.util

import android.text.format.DateUtils
import java.util.Date

fun Date.getRelativeTimeString(): String {
  return DateUtils
    .getRelativeTimeSpanString(
      this.time,
      System.currentTimeMillis(),
      DateUtils.DAY_IN_MILLIS
    )
    .toString()
}