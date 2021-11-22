/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.core.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import tachiyomi.core.di.AppScope
import toothpick.Toothpick
import toothpick.ktp.binding.module
import toothpick.ktp.extension.getInstance

@Composable
actual inline fun <reified VM : BaseViewModel> viewModel(): VM {
  val viewModel = remember {
    AppScope.getInstance<VM>()
  }
  DisposableEffect(viewModel) {
    onDispose {
      viewModel.onDestroy()
    }
  }
  return viewModel
}

@Composable
actual inline fun <reified VM : BaseViewModel, S : Any> viewModel(
  noinline initialState: @DisallowComposableCalls () -> S
): VM {
  val (viewModel, submodule) = remember {
    val submodule = module {
      initialState().let { bind(it.javaClass).toInstance(it) }
    }
    val subscope = AppScope.subscope(submodule).also {
      it.installModules(submodule)
    }
    val viewModel = subscope.getInstance<VM>()
    Pair(viewModel, submodule)
  }
  DisposableEffect(viewModel) {
    onDispose {
      viewModel.onDestroy()
      Toothpick.closeScope(submodule)
    }
  }
  return viewModel
}

@Composable
actual inline fun <reified VM : BaseViewModel, S : Any> viewModel(
  noinline initialState: () -> S,
  saver: Saver<S, Any>
): VM {
  val state = rememberSaveable(init = initialState, saver = saver)

  val (viewModel, submodule) = remember {
    val submodule = module {
      bind(state.javaClass).toInstance(state)
    }
    val subscope = AppScope.subscope(submodule).also {
      it.installModules(submodule)
    }
    val viewModel = subscope.getInstance<VM>()
    Pair(viewModel, submodule)
  }
  DisposableEffect(viewModel) {
    onDispose {
      viewModel.onDestroy()
      Toothpick.closeScope(submodule)
    }
  }
  return viewModel
}
