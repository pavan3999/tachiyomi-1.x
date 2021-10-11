/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.download.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import okio.FileSystem
import tachiyomi.core.di.Inject
import tachiyomi.core.di.Singleton
import tachiyomi.core.util.actor
import tachiyomi.domain.download.service.DownloadManagerActor.Message
import tachiyomi.domain.manga.model.Chapter
import tachiyomi.domain.manga.model.Manga

@Singleton
class DownloadManager @Inject internal constructor(
  private val preferences: DownloadPreferences,
  private val downloader: Downloader,
  private val compressor: DownloadCompressor,
  private val repository: DownloadRepository,
  private val fileSystem: FileSystem
) {

  @Suppress("EXPERIMENTAL_API_USAGE")
  private val actor = GlobalScope.actor<Message>(
    context = Dispatchers.Default,
    capacity = Channel.UNLIMITED
  ) {
    val actor = DownloadManagerActor(
      messages = channel,
      preferences = preferences,
      downloader = downloader,
      compressor = compressor,
      repository = repository,
      fileSystem = fileSystem
    )
    actor.receiveAll()
  }

  fun start() {
    actor.trySend(Message.Start)
  }

  fun stop() {
    actor.trySend(Message.Stop)
  }

  fun add(downloads: Map<Manga, List<Chapter>>) {
    actor.trySend(Message.Add(downloads))
  }

}
