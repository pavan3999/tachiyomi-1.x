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
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.icerock.moko.resources.StringResource
import tachiyomi.domain.ui.model.StartScreen
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.theme.CustomColors

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(startScreen: StartScreen) {
  val startRoute = startScreen.toRoute()
  val navController = rememberAnimatedNavController()
  val currentScreen by navController.currentBackStackEntryAsState()
  val currentRoute = currentScreen?.destination?.route
  val customColors = CustomColors.current

  val (requestedHideBottomNav, requestHideBottomNav) = remember { mutableStateOf(false) }

  DisposableEffect(currentScreen) {
    onDispose {
      requestHideBottomNav(false)
    }
  }

  Scaffold(
    modifier = Modifier
      .background(customColors.bars)
      .navigationBarsPadding(),
    content = {
      Box {
        Navigation(
          navController = navController,
          startScreen = startRoute,
          requestHideNavigator = requestHideBottomNav
        )
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
            val isSelected = currentRoute?.startsWith(it.screen.route) ?: false
            BottomNavigationItem(
              icon = {
                Icon(
                  if (isSelected) it.selectedIcon else it.unselectedIcon,
                  contentDescription = null
                )
              },
              label = {
                Text(localize(it.text), maxLines = 1, overflow = TextOverflow.Ellipsis)
              },
              selected = isSelected,
              onClick = {
                navController.navigate(it.screen.route) {
                  launchSingleTop = true
                  restoreState = true
                  popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                  }
                }
              },
            )
          }
        }
      }
    }
  )
}

private enum class TopLevelRoutes(
  val screen: TopScreen,
  val text: StringResource,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector = selectedIcon,
) {

  Library(
    TopScreen.Library, MR.strings.library_label, Icons.Default.CollectionsBookmark, Icons
      .Outlined.CollectionsBookmark
  ),
  Updates(
    TopScreen.Updates,
    MR.strings.updates_label,
    Icons.Default.NewReleases,
    Icons.Outlined.NewReleases
  ),
  History(TopScreen.History, MR.strings.history_label, Icons.Outlined.History),
  Browse(TopScreen.Browse, MR.strings.browse_label, Icons.Default.Explore, Icons.Outlined.Explore),
  More(TopScreen.More, MR.strings.more_label, Icons.Outlined.MoreHoriz);

  companion object {

    val values = values().toList()
    fun isTopLevelRoute(route: String?): Boolean {
      val nestedRoute = route?.substringAfter("/")
      return nestedRoute != null && values.any { it.screen.route == nestedRoute }
    }
  }
}

private fun StartScreen.toRoute(): TopScreen {
  return when (this) {
    StartScreen.Library -> TopScreen.Library
    StartScreen.Updates -> TopScreen.Updates
    StartScreen.History -> TopScreen.History
    StartScreen.Browse -> TopScreen.Browse
    StartScreen.More -> TopScreen.More
  }
}
