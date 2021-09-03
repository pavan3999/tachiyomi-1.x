/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.core.http

import app.cash.quickjs.QuickJs
import javax.inject.Inject

/**
 * A factory for creating instances of [QuickJS].
 */
internal class QuickJSFactory @Inject constructor() : JSFactory {

  /**
   * Returns a new instance of [QuickJS].
   */
  override fun create(): JS {
    return QuickJS(QuickJs.create())
  }

}
