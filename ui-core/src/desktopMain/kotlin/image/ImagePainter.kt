/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

@file:JvmName("ImagePainterPlatform")

package tachiyomi.ui.core.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter

actual class ImageRequest

actual class ImageMetadata

actual interface ImageRequestListener {
  actual fun onStart(request: ImageRequest)
  actual fun onCancel(request: ImageRequest)
  actual fun onError(request: ImageRequest, throwable: Throwable)
  actual fun onSuccess(request: ImageRequest, metadata: ImageMetadata)
}

actual class ImageRequestBuilder {
  actual fun listener(listener: ImageRequestListener?) {
  }

  actual fun listener(
    onStart: (request: ImageRequest) -> Unit,
    onCancel: (request: ImageRequest) -> Unit,
    onError: (request: ImageRequest, throwable: Throwable) -> Unit,
    onSuccess: (request: ImageRequest, metadata: ImageMetadata) -> Unit
  ) {
  }
}

@Composable
internal actual fun platformRememberImagePainter(
  data: Any?,
  builder: ImageRequestBuilder.() -> Unit
): Painter {
  // TODO(inorichi): find out a way to draw images on JVM
  return ColorPainter(Color.Black)
}
