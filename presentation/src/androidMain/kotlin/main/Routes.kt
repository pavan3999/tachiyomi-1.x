/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.main

sealed class TopScreen(val route: String) {
  object Library : TopScreen("library")
  object Updates : TopScreen("updates")
  object History : TopScreen("history")
  object Browse : TopScreen("browse")
  object More : TopScreen("more")
}

sealed class LeafScreen(val route: String) {
  fun createRoute(root: TopScreen) = "${root.route}/$route"

  object DeepLink : LeafScreen("deeplink/{referrer}?url={url}")

  object Library : LeafScreen("library")

  object Manga : LeafScreen("manga/{mangaId}") {
    fun createRoute(root: TopScreen, mangaId: Long) = "${root.route}/manga/$mangaId"
  }
  object MangaTracking : LeafScreen("manga/{mangaId}/track") {
    fun createRoute(root: TopScreen, mangaId: Long) = "${root.route}/manga/$mangaId/track"
  }

  object Reader : LeafScreen("reader/{chapterId}") {
    fun createRoute(root: TopScreen, chapterId: Long) = "${root.route}/reader/$chapterId"
  }

  object Updates : LeafScreen("updates")

  object History : LeafScreen("history")

  object Browse : LeafScreen("browse")
  object BrowseCatalog : LeafScreen("browse/{sourceId}") {
    fun createRoute(root: TopScreen, sourceId: Long) = "${root.route}/browse/$sourceId"
  }

  object WebView : LeafScreen("webview/{sourceId}/{encodedUrl}") {
    fun createRoute(root: TopScreen, sourceId: Long, encodedUrl: String) =
      "${root.route}/webview/$sourceId/$encodedUrl"
  }

  object More : LeafScreen("more")
  object Categories : LeafScreen("categories")
  object DownloadQueue : LeafScreen("download_queue")

  object About : LeafScreen("about")
  object Licenses : LeafScreen("licenses")

  object Settings : LeafScreen("settings")
  object SettingsGeneral : LeafScreen("settings/general")
  object SettingsAppearance : LeafScreen("settings/appearance")
  object SettingsLibrary : LeafScreen("settings/library")
  object SettingsReader : LeafScreen("settings/reader")
  object SettingsDownloads : LeafScreen("settings/downloads")
  object SettingsTracking : LeafScreen("settings/tracking")
  object SettingsBrowse : LeafScreen("settings/browse")
  object SettingsBackup : LeafScreen("settings/backup")
  object SettingsSecurity : LeafScreen("settings/security")
  object SettingsAdvanced : LeafScreen("settings/advanced")
}
