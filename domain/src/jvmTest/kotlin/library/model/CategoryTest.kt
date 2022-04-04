package tachiyomi.domain.library.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import tachiyomi.domain.library.model.DisplayMode.Companion.displayMode

class CategoryTest : StringSpec({

  "unspecified/default value have changed was this intentional" {
    Category(flags = 0b0000).displayMode shouldBeEqualComparingTo DisplayMode.CompactGrid
  }

  "flags value have changed should look over and setup a migration" {
    Category(flags = 0b0001).displayMode shouldBeEqualComparingTo DisplayMode.ComfortableGrid
    Category(flags = 0b0010).displayMode shouldBeEqualComparingTo DisplayMode.List
    Category(flags = 0b0011).displayMode shouldBeEqualComparingTo DisplayMode.CompactGrid
  }

})