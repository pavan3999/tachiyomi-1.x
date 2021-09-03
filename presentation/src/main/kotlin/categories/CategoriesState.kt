package tachiyomi.ui.categories

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import tachiyomi.domain.library.model.Category

interface CategoriesState {

  val isLoading: Boolean
  val categories: List<Category>
  val isEmpty: Boolean
  var dialog: CategoriesViewModel.Dialog?
}

fun CategoriesState(): CategoriesState {
  return CategoriesStateImpl()
}

class CategoriesStateImpl : CategoriesState {

  override var isLoading: Boolean by mutableStateOf(true)
  override var categories: List<Category> by mutableStateOf(emptyList())
  override val isEmpty: Boolean by derivedStateOf { categories.isEmpty() }
  override var dialog: CategoriesViewModel.Dialog? by mutableStateOf(null)
}