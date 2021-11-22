/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

class AppRippleTheme(
  private val isLightTheme: Boolean
) : RippleTheme {

  val contentColor
    get() = if (isLightTheme) Color.Black else Color.White

  @Composable
  override fun defaultColor(): Color {
    return RippleTheme.defaultRippleColor(contentColor, lightTheme = isLightTheme)
  }

  @Composable
  override fun rippleAlpha(): RippleAlpha {
    return getRippleAlpha(contentColor, lightTheme = isLightTheme)
  }

  private fun getRippleAlpha(contentColor: Color, lightTheme: Boolean): RippleAlpha {
    return when {
      lightTheme -> {
        if (contentColor.luminance() > 0.5) {
          LightThemeHighContrastRippleAlpha
        } else {
          LightThemeLowContrastRippleAlpha
        }
      }
      else -> {
        DarkThemeRippleAlpha
      }
    }
  }
}

private val LightThemeHighContrastRippleAlpha = RippleAlpha(
  pressedAlpha = 0.20f,
  focusedAlpha = 0.20f,
  draggedAlpha = 0.12f,
  hoveredAlpha = 0.04f
)

private val LightThemeLowContrastRippleAlpha = RippleAlpha(
  pressedAlpha = 0.08f,
  focusedAlpha = 0.08f,
  draggedAlpha = 0.04f,
  hoveredAlpha = 0.02f
)

private val DarkThemeRippleAlpha = RippleAlpha(
  pressedAlpha = 0.06f,
  focusedAlpha = 0.08f,
  draggedAlpha = 0.04f,
  hoveredAlpha = 0.02f
)
