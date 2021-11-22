/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.material.Colors as MaterialColors

/**
 * All the application colors from both [MaterialTheme.colors] and [ExtraColors] which can be
 * accessed through this class.
 */
@Stable
@Suppress("unused")
class AppColors(
  private val materialColors: MaterialColors,
  private val extraColors: ExtraColors,
) {

  val primary get() = materialColors.primary
  val primaryVariant get() = materialColors.primaryVariant
  val secondary get() = materialColors.secondary
  val secondaryVariant get() = materialColors.secondaryVariant
  val background get() = materialColors.background
  val surface get() = materialColors.surface
  val error get() = materialColors.error
  val onPrimary get() = materialColors.onPrimary
  val onSecondary get() = materialColors.onSecondary
  val onBackground get() = materialColors.onBackground
  val onSurface get() = materialColors.onSurface
  val onError get() = materialColors.onError
  val isLight get() = materialColors.isLight

  val bars get() = extraColors.bars
  val onBars get() = extraColors.onBars
  val isBarLight get() = extraColors.isBarLight

  companion object {
    val current: AppColors
      @Composable
      @ReadOnlyComposable
      get() = LocalAppColors.current
  }

}

/**
 * Composable that provides the [LocalAppColors] composition to children composables with the
 * [materialColors] and [extraColors] provided.
 */
@Composable
fun AppColors(
  materialColors: MaterialColors,
  extraColors: ExtraColors,
  content: @Composable () -> Unit
) {
  val rememberedCustomColors = remember { extraColors }.apply { updateFrom(extraColors) }
  val rememberedAppColors = remember { AppColors(materialColors, rememberedCustomColors) }

  MaterialTheme(colors = materialColors) {
    CompositionLocalProvider(
      LocalAppColors provides rememberedAppColors,
      content = content
    )
  }
}

private val LocalAppColors = staticCompositionLocalOf<AppColors> {
  error("The AppColors composable must be called before usage")
}
