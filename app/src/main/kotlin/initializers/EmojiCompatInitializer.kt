/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.app.initializers

import android.app.Application
import androidx.core.provider.FontRequest
import androidx.emoji2.text.DefaultEmojiCompatConfig
import androidx.emoji2.text.EmojiCompat
import androidx.emoji2.text.FontRequestEmojiCompatConfig
import tachiyomi.app.R
import javax.inject.Inject

class EmojiCompatInitializer @Inject constructor(context: Application) {

  init {
    // Note: if play services are not available, emoji fonts won't be downloaded
    val config = DefaultEmojiCompatConfig.create(context) ?: FontRequestEmojiCompatConfig(
      context,
      FontRequest(
        "com.google.android.gms.fonts",
        "com.google.android.gms",
        "Noto Color Emoji Compat",
        R.array.com_google_android_gms_fonts_certs
      )
    )
    EmojiCompat.init(config.setReplaceAll(true))
  }

}
