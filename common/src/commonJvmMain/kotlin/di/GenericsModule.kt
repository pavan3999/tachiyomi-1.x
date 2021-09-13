/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.core.di

import toothpick.Scope
import javax.inject.Provider

class GenericsProvider<T>(private val cls: Class<T>, val scope: Scope = AppScope) : Provider<T> {

  override fun get(): T {
    return scope.getInstance(cls)
  }
}
