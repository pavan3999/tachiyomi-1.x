/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

expect class ImageRequest

expect class ImageMetadata

expect interface ImageRequestListener {
  fun onStart(request: ImageRequest)
  fun onCancel(request: ImageRequest)
  fun onError(request: ImageRequest, throwable: Throwable)
  fun onSuccess(request: ImageRequest, metadata: ImageMetadata)
}

expect class ImageRequestBuilder {
  fun listener(listener: ImageRequestListener?)
  fun listener(
    onStart: (request: ImageRequest) -> Unit = {},
    onCancel: (request: ImageRequest) -> Unit = {},
    onError: (request: ImageRequest, throwable: Throwable) -> Unit = { _, _ -> },
    onSuccess: (request: ImageRequest, metadata: ImageMetadata) -> Unit = { _, _ -> }
  )
}

@Composable
fun rememberImagePainter(
  data: Any?,
  builder: ImageRequestBuilder.() -> Unit = {}
): Painter {
  return platformRememberImagePainter(data, builder)
}

@Composable
internal expect fun platformRememberImagePainter(
  data: Any?,
  builder: ImageRequestBuilder.() -> Unit
): Painter
