/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.theme

import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import tachiyomi.core.prefs.Preference
import tachiyomi.domain.ui.UiPreferences
import tachiyomi.ui.core.prefs.PreferenceMutableState
import tachiyomi.ui.core.prefs.asColor
import tachiyomi.ui.core.prefs.asStateIn

/**
 * The application colors that can be customized by the user (overriding the current theme colors).
 */
data class CustomizableAppColorsPreference(
  val primary: Preference<Color>,
  val secondary: Preference<Color>,
  val bars: Preference<Color>
)

class CustomizableAppColorsPreferenceState(
  val primaryState: PreferenceMutableState<Color>,
  val secondaryState: PreferenceMutableState<Color>,
  val barsState: PreferenceMutableState<Color>
) {
  val primary by primaryState
  val secondary by secondaryState
  val bars by barsState
}

fun UiPreferences.getLightColors(): CustomizableAppColorsPreference {
  return CustomizableAppColorsPreference(
    colorPrimaryLight().asColor(),
    colorSecondaryLight().asColor(),
    colorBarsLight().asColor()
  )
}

fun UiPreferences.getDarkColors(): CustomizableAppColorsPreference {
  return CustomizableAppColorsPreference(
    colorPrimaryDark().asColor(),
    colorSecondaryDark().asColor(),
    colorBarsDark().asColor()
  )
}

fun CustomizableAppColorsPreference.asState(scope: CoroutineScope): CustomizableAppColorsPreferenceState {
  return CustomizableAppColorsPreferenceState(
    primary.asStateIn(scope),
    secondary.asStateIn(scope),
    bars.asStateIn(scope)
  )
}
