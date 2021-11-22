/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import tachiyomi.ui.browse.CatalogsScreen
import tachiyomi.ui.browse.catalog.CatalogScreen
import tachiyomi.ui.categories.CategoriesScreen
import tachiyomi.ui.deeplink.DeepLinkHandlerScreen
import tachiyomi.ui.downloads.DownloadQueueScreen
import tachiyomi.ui.history.HistoryScreen
import tachiyomi.ui.library.LibraryScreen
import tachiyomi.ui.manga.MangaScreen
import tachiyomi.ui.more.MoreScreen
import tachiyomi.ui.more.about.AboutScreen
import tachiyomi.ui.more.about.LicensesScreen
import tachiyomi.ui.more.settings.SettingsAdvancedScreen
import tachiyomi.ui.more.settings.SettingsAppearance
import tachiyomi.ui.more.settings.SettingsBackupScreen
import tachiyomi.ui.more.settings.SettingsBrowseScreen
import tachiyomi.ui.more.settings.SettingsDownloadsScreen
import tachiyomi.ui.more.settings.SettingsGeneralScreen
import tachiyomi.ui.more.settings.SettingsLibraryScreen
import tachiyomi.ui.more.settings.SettingsReaderScreen
import tachiyomi.ui.more.settings.SettingsScreen
import tachiyomi.ui.more.settings.SettingsSecurityScreen
import tachiyomi.ui.more.settings.SettingsTrackingScreen
import tachiyomi.ui.reader.ReaderScreen
import tachiyomi.ui.track.TrackScreen
import tachiyomi.ui.updates.UpdatesScreen
import tachiyomi.ui.webview.WebViewScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
@Composable
internal fun Navigation(
  navController: NavHostController,
  startScreen: TopScreen,
  requestHideNavigator: (Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  AnimatedNavHost(
    navController = navController,
    startDestination = startScreen.route,
    modifier = modifier,
    enterTransition = { _, _ -> fadeIn(animationSpec = tween(700)) },
    exitTransition = { _, _ -> fadeOut(animationSpec = tween(700)) },
  ) {
    addLibraryTopLevel(navController, requestHideNavigator)
    addUpdatesTopLevel(navController)
    addHistoryTopLevel(navController)
    addBrowseTopLevel(navController)
    addMoreTopLevel(navController)
    addDeepLinkHandler(navController)
  }
}

@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addLibraryTopLevel(
  navController: NavController,
  requestHideNavigator: (Boolean) -> Unit
) {
  val topScreen = TopScreen.Library
  navigation(
    route = topScreen.route,
    startDestination = LeafScreen.Library.createRoute(topScreen)
  ) {
    addLibrary(navController, topScreen, requestHideNavigator)
    addManga(navController, topScreen)
    addReader(navController, topScreen)
    addWebView(navController, topScreen)
  }
}

@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addUpdatesTopLevel(
  navController: NavController
) {
  val topScreen = TopScreen.Updates
  navigation(
    route = topScreen.route,
    startDestination = LeafScreen.Updates.createRoute(topScreen)
  ) {
    addUpdates(navController, topScreen)
    addManga(navController, topScreen)
    addReader(navController, topScreen)
    addWebView(navController, topScreen)
  }
}

@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addHistoryTopLevel(
  navController: NavController
) {
  val topScreen = TopScreen.History
  navigation(
    route = topScreen.route,
    startDestination = LeafScreen.History.createRoute(topScreen)
  ) {
    addHistory(navController, topScreen)
    addManga(navController, topScreen)
    addReader(navController, topScreen)
    addWebView(navController, topScreen)
  }
}

@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addBrowseTopLevel(
  navController: NavController
) {
  val topScreen = TopScreen.Browse
  navigation(
    route = topScreen.route,
    startDestination = LeafScreen.Browse.createRoute(topScreen)
  ) {
    addBrowse(navController, topScreen)
    addBrowseCatalog(navController, topScreen)
    addManga(navController, topScreen)
    addReader(navController, topScreen)
    addWebView(navController, topScreen)
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addMoreTopLevel(
  navController: NavController
) {
  val topScreen = TopScreen.More
  navigation(
    route = topScreen.route,
    startDestination = LeafScreen.More.createRoute(topScreen)
  ) {
    addMore(navController, topScreen)
    addDownloads(navController, topScreen)
    addCategories(navController, topScreen)
    addAbout(navController, topScreen)
    addLicenses(navController, topScreen)
    addSettings(navController, topScreen)
    addSettingsGeneral(navController, topScreen)
    addSettingsAppearance(navController, topScreen)
    addSettingsLibrary(navController, topScreen)
    addSettingsReader(navController, topScreen)
    addSettingsDownloads(navController, topScreen)
    addSettingsTracking(navController, topScreen)
    addSettingsBrowse(navController, topScreen)
    addSettingsBackup(navController, topScreen)
    addSettingsSecurity(navController, topScreen)
    addSettingsAdvanced(navController, topScreen)
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addLibrary(
  navController: NavController,
  root: TopScreen,
  requestHideNavigator: (Boolean) -> Unit
) {
  composable(LeafScreen.Library.createRoute(root)) {
    LibraryScreen(
      requestHideBottomNav = requestHideNavigator,
      openManga = { mangaId ->
        navController.navigate(LeafScreen.Manga.createRoute(root, mangaId))
      }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addUpdates(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.Updates.createRoute(root)) {
    UpdatesScreen(
      openChapter = { chapterId ->
        navController.navigate(LeafScreen.Reader.createRoute(root, chapterId))
      },
      openManga = { mangaId ->
        navController.navigate(LeafScreen.Manga.createRoute(root, mangaId))
      }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addHistory(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.History.createRoute(root)) {
    HistoryScreen(
      openChapter = { chapterId ->
        navController.navigate(LeafScreen.Reader.createRoute(root, chapterId))
      },
      openManga = { mangaId ->
        navController.navigate(LeafScreen.Manga.createRoute(root, mangaId))
      }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addBrowse(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.Browse.createRoute(root)) {
    CatalogsScreen(
      openCatalog = { sourceId ->
        navController.navigate(LeafScreen.BrowseCatalog.createRoute(root, sourceId))
      }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addMore(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.More.createRoute(root)) {
    MoreScreen(
      openDownloads = { navController.navigate(LeafScreen.DownloadQueue.createRoute(root)) },
      openCategories = { navController.navigate(LeafScreen.Categories.createRoute(root)) },
      openSettings = { navController.navigate(LeafScreen.Settings.createRoute(root)) },
      openAbout = { navController.navigate(LeafScreen.About.createRoute(root)) }
    )
  }
}

@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addManga(
  navController: NavController,
  root: TopScreen
) {
  composable(
    route = LeafScreen.Manga.createRoute(root),
    arguments = listOf(
      navArgument("mangaId") { type = NavType.LongType }
    )
  ) { entry ->
    val mangaId = entry.arguments?.getLong("mangaId") as Long
    MangaScreen(
      mangaId = mangaId,
      navigateUp = { navController.navigateUp() },
      openChapter = { chapterId ->
        navController.navigate(LeafScreen.Reader.createRoute(root, chapterId))
      },
      openTrack = {
        navController.navigate(LeafScreen.MangaTracking.createRoute(root, mangaId))
      },
      openWebView = { sourceId, url ->
        navController.navigate(LeafScreen.WebView.createRoute(root, sourceId, url))
      }
    )
  }
  bottomSheet(
    route = LeafScreen.MangaTracking.createRoute(root),
    arguments = listOf(
      navArgument("mangaId") { type = NavType.LongType }
    )
  ) { entry ->
    val mangaId = entry.arguments?.getLong("mangaId") as Long
    TrackScreen(
      mangaId = mangaId
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWebView(
  navController: NavController,
  root: TopScreen
) {
  composable(
    route = LeafScreen.WebView.createRoute(root),
    arguments = listOf(
      navArgument("sourceId") { type = NavType.LongType },
      navArgument("encodedUrl") { type = NavType.StringType }
    )
  ) { entry ->
    val sourceId = entry.arguments?.getLong("sourceId") as Long
    val encodedUrl = entry.arguments?.getString("encodedUrl") as String
    val url = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
    WebViewScreen(
      sourceId = sourceId,
      url = url,
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addReader(
  navController: NavController,
  root: TopScreen
) {
  composable(
    route = LeafScreen.Reader.createRoute(root),
    arguments = listOf(
      navArgument("chapterId") { type = NavType.LongType }
    )
  ) { entry ->
    val chapterId = entry.arguments?.getLong("chapterId") as Long
    ReaderScreen(
      chapterId = chapterId
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addBrowseCatalog(
  navController: NavController,
  root: TopScreen
) {
  composable(
    route = LeafScreen.BrowseCatalog.createRoute(root),
    arguments = listOf(
      navArgument("sourceId") { type = NavType.LongType }
    )
  ) { entry ->
    val sourceId = entry.arguments?.getLong("sourceId") as Long
    CatalogScreen(
      sourceId = sourceId,
      navigateUp = { navController.navigateUp() },
      openManga = { navController.navigate(LeafScreen.Manga.createRoute(root, it)) }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addDownloads(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.DownloadQueue.createRoute(root)) {
    DownloadQueueScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCategories(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.Categories.createRoute(root)) {
    CategoriesScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addAbout(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.About.createRoute(root)) {
    AboutScreen(
      openLicenses = { navController.navigate(LeafScreen.Licenses.createRoute(root)) },
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addLicenses(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.Licenses.createRoute(root)) {
    LicensesScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettings(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.Settings.createRoute(root)) {
    SettingsScreen(
      openSettingsGeneral = {
        navController.navigate(LeafScreen.SettingsGeneral.createRoute(root))
      },
      openSettingsAppearance = {
        navController.navigate(LeafScreen.SettingsAppearance.createRoute(root))
      },
      openSettingsLibrary = {
        navController.navigate(LeafScreen.SettingsLibrary.createRoute(root))
      },
      openSettingsReader = {
        navController.navigate(LeafScreen.SettingsReader.createRoute(root))
      },
      openSettingsDownloads = {
        navController.navigate(LeafScreen.SettingsDownloads.createRoute(root))
      },
      openSettingsTracking = {
        navController.navigate(LeafScreen.SettingsTracking.createRoute(root))
      },
      openSettingsBrowse = {
        navController.navigate(LeafScreen.SettingsBrowse.createRoute(root))
      },
      openSettingsBackup = {
        navController.navigate(LeafScreen.SettingsBackup.createRoute(root))
      },
      openSettingsSecurity = {
        navController.navigate(LeafScreen.SettingsSecurity.createRoute(root))
      },
      openSettingsAdvanced = {
        navController.navigate(LeafScreen.SettingsAdvanced.createRoute(root))
      },
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettingsGeneral(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.SettingsGeneral.createRoute(root)) {
    SettingsGeneralScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettingsAppearance(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.SettingsAppearance.createRoute(root)) {
    SettingsAppearance(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettingsLibrary(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.SettingsLibrary.createRoute(root)) {
    SettingsLibraryScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettingsReader(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.SettingsReader.createRoute(root)) {
    SettingsReaderScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettingsDownloads(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.SettingsDownloads.createRoute(root)) {
    SettingsDownloadsScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettingsTracking(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.SettingsTracking.createRoute(root)) {
    SettingsTrackingScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettingsBrowse(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.SettingsBrowse.createRoute(root)) {
    SettingsBrowseScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettingsBackup(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.SettingsBackup.createRoute(root)) {
    SettingsBackupScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettingsSecurity(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.SettingsSecurity.createRoute(root)) {
    SettingsSecurityScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettingsAdvanced(
  navController: NavController,
  root: TopScreen
) {
  composable(LeafScreen.SettingsAdvanced.createRoute(root)) {
    SettingsAdvancedScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addDeepLinkHandler(
  navController: NavController
) {
  composable(
    route = LeafScreen.DeepLink.route,
    arguments = listOf(
      navArgument("referrer") { type = NavType.StringType },
      navArgument("url") { type = NavType.StringType },
    ),
    deepLinks = listOf(navDeepLink {
      uriPattern = "tachiyomi://deeplink/{referrer}?url={url}"
    }),
  ) { entry ->
    val referrer = entry.arguments?.getString("referrer") as String
    val url = entry.arguments?.getString("url") as String

    DeepLinkHandlerScreen(
      referrer = referrer,
      url = url,
      navigateUp = { navController.navigateUp() }
    )
  }
}
