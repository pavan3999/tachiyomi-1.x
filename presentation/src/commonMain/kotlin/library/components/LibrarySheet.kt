/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.TriStateCheckbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tachiyomi.domain.library.model.DisplayMode
import tachiyomi.domain.library.model.LibraryFilter
import tachiyomi.domain.library.model.LibraryFilter.Value.*
import tachiyomi.domain.library.model.LibrarySort
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.core.components.ChoiceChip
import tachiyomi.ui.core.components.FlowRow
import tachiyomi.ui.core.components.HorizontalPager
import tachiyomi.ui.core.components.pagerTabIndicatorOffset
import tachiyomi.ui.core.components.rememberPagerState
import tachiyomi.ui.core.providers.LocalWindow
import tachiyomi.ui.core.theme.AppColors
import tachiyomi.ui.core.viewmodel.viewModel
import tachiyomi.ui.library.LibrarySheetViewModel
import kotlin.math.round

// TODO: move this to a route
@Composable
fun LibrarySheet(
  currentPage: Int,
  onPageChanged: (Int) -> Unit
) {
  val vm = viewModel<LibrarySheetViewModel>()
  val scope = rememberCoroutineScope()
  val pagerState = rememberPagerState(currentPage)
  LaunchedEffect(pagerState) {
    snapshotFlow { pagerState.currentPage }.collect {
      onPageChanged(it)
    }
  }
  val window = LocalWindow.current

  TabRow(
    modifier = Modifier.requiredHeight(48.dp),
    selectedTabIndex = currentPage,
    backgroundColor = AppColors.current.bars,
    contentColor = AppColors.current.onBars,
    indicator = { TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, it)) }
  ) {
    listOf("Filter", "Sort", "Display").forEachIndexed { i, title ->
      Tab(
        selected = currentPage == i,
        onClick = { scope.launch { pagerState.animateScrollToPage(i) } }
      ) {
        Text(title)
      }
    }
  }
  HorizontalPager(
    count = 3,
    state = pagerState,
    verticalAlignment = Alignment.Top,
  ) { page ->
    LazyColumn {
      when (page) {
        0 -> FiltersPage(filters = vm.filters, onClick = vm::toggleFilter)
        1 -> SortPage(sorting = vm.sorting, onClick = vm::toggleSort)
        2 -> {
          val (columns, setColumns) =
            if (window.screenWidthDp > window.screenHeightDp) {
              vm.columnsInLandscape to vm::changeColumnsInLandscape
            } else {
              vm.columnsInPortrait to vm::changeColumnsInPortrait
            }

          DisplayPage(
            displayMode = vm.displayMode,
            columns = columns,
            downloadBadges = vm.downloadBadges,
            unreadBadges = vm.unreadBadges,
            categoryTabs = vm.showCategoryTabs,
            allCategory = vm.showAllCategory,
            countInCategory = vm.showCountInCategory,
            onClickDisplayMode = vm::changeDisplayMode,
            onChangeColumns = setColumns,
            onClickDownloadBadges = vm::toggleDownloadBadges,
            onClickUnreadBadges = vm::toggleUnreadBadges,
            onClickCategoryTabs = vm::toggleShowCategoryTabs,
            onClickAllCategory = vm::toggleShowAllCategory,
            onClickCountInCategory = vm::toggleShowCountInCategory
          )
        }
      }
    }
  }
}

private fun LazyListScope.FiltersPage(
  filters: List<LibraryFilter>,
  onClick: (LibraryFilter.Type) -> Unit
) {
  items(filters) { (filter, state) ->
    ClickableRow(onClick = { onClick(filter) }) {
      TriStateCheckbox(
        modifier = Modifier.padding(horizontal = 16.dp),
        state = state.asToggleableState(),
        onClick = { onClick(filter) }
      )
      Text(filter.name)
    }
  }
}

private fun LazyListScope.SortPage(
  sorting: LibrarySort,
  onClick: (LibrarySort.Type) -> Unit
) {
  items(LibrarySort.types) { type ->
    ClickableRow(onClick = { onClick(type) }) {
      val iconModifier = Modifier.requiredWidth(56.dp)
      if (sorting.type == type) {
        val icon = if (sorting.isAscending) {
          Icons.Default.KeyboardArrowUp
        } else {
          Icons.Default.KeyboardArrowDown
        }
        Icon(icon, null, iconModifier, MaterialTheme.colors.primary)
      } else {
        Spacer(iconModifier)
      }
      Text(type.name)
    }
  }
}

private fun LazyListScope.DisplayPage(
  displayMode: DisplayMode,
  columns: Int,
  downloadBadges: Boolean,
  unreadBadges: Boolean,
  categoryTabs: Boolean,
  allCategory: Boolean,
  countInCategory: Boolean,
  onClickDisplayMode: (DisplayMode) -> Unit,
  onChangeColumns: (Int) -> Unit,
  onClickDownloadBadges: () -> Unit,
  onClickUnreadBadges: () -> Unit,
  onClickCategoryTabs: () -> Unit,
  onClickAllCategory: () -> Unit,
  onClickCountInCategory: () -> Unit
) {
  item {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
      Text(
        text = "Display mode".uppercase(),
        modifier = Modifier.padding(bottom = 12.dp),
        style = MaterialTheme.typography.subtitle2,
        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
      )
      FlowRow(mainAxisSpacing = 4.dp) {
        DisplayMode.values.forEach {
          ChoiceChip(
            isSelected = it == displayMode,
            onClick = { onClickDisplayMode(it) },
            content = { Text(it.name) }
          )
        }
      }
      Text(
        localize(
          MR.strings.columns_num_label, if (columns > 1) columns else
            localize(MR.strings.columns_auto)
        ), Modifier.padding(top = 8.dp)
      )
      val window = LocalWindow.current
      val maxValue = round(window.screenWidthDp.dp / 64.dp)
      var columnsFloat by remember(window) { mutableStateOf(columns.toFloat()) }
      Slider(
        value = columnsFloat,
        onValueChange = {
          columnsFloat = it
          onChangeColumns(round(it).toInt())
        },
        enabled = displayMode != DisplayMode.List,
        valueRange = 1f..maxValue,
        steps = maxValue.toInt() - 2
      )
    }
  }
  item {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
      Text(
        text = localize(MR.strings.badges_label).uppercase(),
        modifier = Modifier.padding(bottom = 12.dp),
        style = MaterialTheme.typography.subtitle2,
        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
      )
      FlowRow(mainAxisSpacing = 4.dp) {
        ChoiceChip(
          isSelected = unreadBadges,
          onClick = { onClickUnreadBadges() },
          content = { Text(localize(MR.strings.unread_label)) }
        )
        ChoiceChip(
          isSelected = downloadBadges,
          onClick = { onClickDownloadBadges() },
          content = { Text(localize(MR.strings.downloaded_label)) }
        )
      }
    }
  }
  item {
    Text(
      text = "Tabs".uppercase(),
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
      style = MaterialTheme.typography.subtitle2,
      color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
    )
  }
  item {
    ClickableRow(onClick = { onClickCategoryTabs() }) {
      Checkbox(
        modifier = Modifier.padding(horizontal = 16.dp),
        checked = categoryTabs,
        onCheckedChange = null
      )
      Text(localize(MR.strings.display_category_tabs))
    }
  }
  item {
    ClickableRow(onClick = { onClickAllCategory() }) {
      Checkbox(
        modifier = Modifier.padding(horizontal = 16.dp),
        checked = allCategory,
        onCheckedChange = null
      )
      Text(localize(MR.strings.display_all_category))
    }
  }
  item {
    ClickableRow(onClick = { onClickCountInCategory() }) {
      Checkbox(
        modifier = Modifier.padding(horizontal = 16.dp),
        checked = countInCategory,
        onCheckedChange = null
      )
      Text(localize(MR.strings.display_category_numbers))
    }
  }
}

@Composable
private fun ClickableRow(onClick: () -> Unit, content: @Composable () -> Unit) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .requiredHeight(48.dp)
      .clickable(onClick = onClick),
    verticalAlignment = Alignment.CenterVertically,
    content = { content() }
  )
}

private fun LibraryFilter.Value.asToggleableState(): ToggleableState {
  return when (this) {
    Included -> ToggleableState.On
    Excluded -> ToggleableState.Indeterminate
    Missing -> ToggleableState.Off
  }
}
