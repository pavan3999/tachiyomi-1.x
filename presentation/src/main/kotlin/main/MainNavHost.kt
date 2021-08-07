/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.outlined.CollectionsBookmark
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.NewReleases
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import tachiyomi.ui.R
import tachiyomi.ui.browse.CatalogsScreen
import tachiyomi.ui.browse.catalog.CatalogScreen
import tachiyomi.ui.categories.CategoriesScreen
import tachiyomi.ui.core.theme.CustomColors
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
import tachiyomi.ui.more.settings.SettingsParentalControlsScreen
import tachiyomi.ui.more.settings.SettingsReaderScreen
import tachiyomi.ui.more.settings.SettingsScreen
import tachiyomi.ui.more.settings.SettingsSecurityScreen
import tachiyomi.ui.more.settings.SettingsTrackingScreen
import tachiyomi.ui.reader.ReaderScreen
import tachiyomi.ui.updates.UpdatesScreen
import tachiyomi.ui.webview.WebViewScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(startRoute: Route): NavHostController {
  val navController = rememberNavController()
  val currentScreen by navController.currentBackStackEntryAsState()
  val currentRoute = currentScreen?.destination?.route

  val (requestedHideBottomNav, requestHideBottomNav) = remember { mutableStateOf(false) }

  DisposableEffect(currentScreen) {
    onDispose {
      requestHideBottomNav(false)
    }
  }

  Scaffold(
    modifier = Modifier.navigationBarsPadding(),
    content = {
      Box {
        NavHost(navController, startDestination = startRoute.id) {
          // TODO: Have a NavHost per individual top-level route?

          composable(Route.Library.id) { LibraryScreen(navController, requestHideBottomNav) }
          composable(
            "${Route.LibraryManga.id}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
          ) { backStackEntry ->
            val mangaId = backStackEntry.arguments?.getLong("id") as Long
            MangaScreen(navController, mangaId)
          }

          composable(
            "${Route.Reader.id}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
          ) { backStackEntry ->
            val chapterId = backStackEntry.arguments?.getLong("id") as Long
            ReaderScreen(navController, chapterId)
          }

          composable(Route.Updates.id) { UpdatesScreen(navController) }

          composable(Route.History.id) { HistoryScreen(navController) }

          composable(Route.Browse.id) { CatalogsScreen(navController) }
          composable(
            "${Route.BrowseCatalog.id}/{sourceId}",
            arguments = listOf(navArgument("sourceId") { type = NavType.LongType })
          ) { backStackEntry ->
            val sourceId = backStackEntry.arguments?.getLong("sourceId") as Long
            CatalogScreen(navController, sourceId)
          }
          composable(
            "${Route.BrowseCatalogManga.id}/{sourceId}/{mangaId}",
            arguments = listOf(
              navArgument("sourceId") { type = NavType.LongType },
              navArgument("mangaId") { type = NavType.LongType },
            )
          ) { backStackEntry ->
            val mangaId = backStackEntry.arguments?.getLong("mangaId") as Long
            MangaScreen(navController, mangaId)
          }

          composable(
            "${Route.WebView.id}/{sourceId}/{encodedUrl}",
            arguments = listOf(
              navArgument("sourceId") { type = NavType.LongType },
              navArgument("encodedUrl") { type = NavType.StringType },
            )
          ) { backStackEntry ->
            val sourceId = backStackEntry.arguments?.getLong("sourceId") as Long
            val encodedUrl = backStackEntry.arguments?.getString("encodedUrl") as String
            val url = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
            WebViewScreen(navController, sourceId, url)
          }

          composable(Route.More.id) { MoreScreen(navController) }
          composable(Route.Categories.id) { CategoriesScreen(navController) }
          composable(Route.DownloadQueue.id) { DownloadQueueScreen(navController) }

          composable(Route.About.id) { AboutScreen(navController) }
          composable(Route.Licenses.id) { LicensesScreen(navController) }

          composable(Route.Settings.id) { SettingsScreen(navController) }
          composable(Route.SettingsGeneral.id) { SettingsGeneralScreen(navController) }
          composable(Route.SettingsAppearance.id) { SettingsAppearance(navController) }
          composable(Route.SettingsLibrary.id) { SettingsLibraryScreen(navController) }
          composable(Route.SettingsReader.id) { SettingsReaderScreen(navController) }
          composable(Route.SettingsDownloads.id) { SettingsDownloadsScreen(navController) }
          composable(Route.SettingsTracking.id) { SettingsTrackingScreen(navController) }
          composable(Route.SettingsBrowse.id) { SettingsBrowseScreen(navController) }
          composable(Route.SettingsBackup.id) { SettingsBackupScreen(navController) }
          composable(Route.SettingsSecurity.id) { SettingsSecurityScreen(navController) }
          composable(Route.SettingsParentalControls.id) {
            SettingsParentalControlsScreen(navController)
          }
          composable(Route.SettingsAdvanced.id) { SettingsAdvancedScreen(navController) }
        }
      }
    },
    bottomBar = {
      val isVisible = TopLevelRoutes.isTopLevelRoute(currentRoute) && !requestedHideBottomNav

      AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
      ) {
        BottomNavigation(
          backgroundColor = CustomColors.current.bars,
          contentColor = CustomColors.current.onBars,
        ) {
          TopLevelRoutes.values.forEach {
            val isSelected = currentRoute == it.route.id
            BottomNavigationItem(
              icon = {
                Icon(
                  if (isSelected) it.selectedIcon else it.unselectedIcon,
                  contentDescription = null
                )
              },
              label = {
                Text(stringResource(it.text), maxLines = 1, overflow = TextOverflow.Ellipsis)
              },
              selected = isSelected,
              onClick = {
                if (currentRoute != it.route.id) {
                  navController.popBackStack(navController.graph.startDestinationId, false)
                  navController.navigate(it.route.id)
                }
              },
            )
          }
        }
      }
    }
  )

  return navController
}

private enum class TopLevelRoutes(
  val route: Route,
  val text: Int,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector = selectedIcon,
) {

  Library(
    Route.Library, R.string.library_label, Icons.Default.CollectionsBookmark, Icons
      .Outlined.CollectionsBookmark
  ),
  Updates(
    Route.Updates,
    R.string.updates_label,
    Icons.Default.NewReleases,
    Icons.Outlined.NewReleases
  ),
  History(Route.History, R.string.history_label, Icons.Outlined.History),
  Browse(Route.Browse, R.string.browse_label, Icons.Default.Explore, Icons.Outlined.Explore),
  More(Route.More, R.string.more_label, Icons.Outlined.MoreHoriz);

  companion object {

    val values = values().toList()
    fun isTopLevelRoute(route: String?): Boolean {
      return route != null && values.any { it.route.id == route }
    }
  }
}
