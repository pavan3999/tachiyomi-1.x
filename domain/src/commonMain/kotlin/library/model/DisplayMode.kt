/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.library.model

import base.model.Flag
import base.model.Mask
import tachiyomi.core.prefs.Preference

enum class DisplayMode(override val flag: Long) : Flag {
  ComfortableGrid(0b0001L),
  List(0b0010L),
  CompactGrid(0b0011L);

  companion object : Mask<DisplayMode> {
    override val mask = 0b0111L
    override val values = values()

    val Category.displayMode: DisplayMode
      get() {
        return getFlag(flags) ?: CompactGrid
      }

    fun Category.set(value: DisplayMode): Category {
      return copy(flags = setFlag(flags, value))
    }

    fun Preference<Long>.set(value: DisplayMode): Long {
      val flags = setFlag(get(), value)
      set(flags)
      return flags
    }
  }
}
