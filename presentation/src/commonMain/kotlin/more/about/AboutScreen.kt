/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.more.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Public
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import tachiyomi.i18n.Images
import tachiyomi.i18n.MR
import tachiyomi.ui.core.components.BackIconButton
import tachiyomi.ui.core.prefs.PreferenceRow
import tachiyomi.ui.more.components.LinkIcon
import tachiyomi.ui.more.components.LogoHeaderScaffold

@Composable
fun AboutScreen(
  openLicenses: () -> Unit,
  navigateUp: () -> Unit
) {
  val uriHandler = LocalUriHandler.current

  LogoHeaderScaffold(
    titleResId = MR.strings.about_label,
    navigationIcon = { BackIconButton(navigateUp) },
  ) {
    LazyColumn {
      item {
        PreferenceRow(
          title = MR.strings.version_label,
          onClick = {
            // TODO
          },
        )
      }

      item {
        PreferenceRow(
          title = MR.strings.whats_new_label,
          onClick = {
            // TODO
          },
        )
      }

      item {
        PreferenceRow(
          title = MR.strings.translate_label,
          onClick = { uriHandler.openUri("https://tachiyomi.org/help/contribution/#translation") },
        )
      }

      item {
        PreferenceRow(
          title = MR.strings.licenses_label,
          onClick = openLicenses,
        )
      }

      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
        ) {
          LinkIcon(
            labelRes = MR.strings.website_label,
            icon = Icons.Outlined.Public,
            url = "https://tachiyomi.org",
          )
          LinkIcon(
            labelRes = MR.strings.discord_label,
            icon = Images.discord(),
            url = "https://discord.gg/tachiyomi",
          )
          LinkIcon(
            labelRes = MR.strings.twitter_label,
            icon = Images.twitter(),
            url = "https://twitter.com/tachiyomiorg",
          )
          LinkIcon(
            labelRes = MR.strings.facebook_label,
            icon = Images.facebook(),
            url = "https://facebook.com/tachiyomiorg",
          )
          LinkIcon(
            labelRes = MR.strings.reddit_label,
            icon = Images.reddit(),
            url = "https://www.reddit.com/r/Tachiyomi",
          )
          LinkIcon(
            labelRes = MR.strings.github_label,
            icon = Images.github(),
            url = "https://github.com/tachiyomiorg",
          )
        }
      }
    }
  }
}
