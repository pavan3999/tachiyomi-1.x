/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.browse

import androidx.compose.ui.text.intl.LocaleList
import tachiyomi.domain.catalog.model.CatalogLocal

class UserLanguagesComparator : Comparator<Language> {

  private val userLanguages = mutableMapOf<String, Int>()

  init {
    val userLocales = LocaleList.current.localeList
    val size = userLocales.size
    for (locale in userLocales) {
      userLanguages[locale.language] = size - userLanguages.size
    }
  }

  override fun compare(a: Language, b: Language): Int {
    val langOnePosition = userLanguages[a.code] ?: 0
    val langTwoPosition = userLanguages[b.code] ?: 0

    return langTwoPosition.compareTo(langOnePosition)
  }

}

class InstalledLanguagesComparator(
  localCatalogs: List<CatalogLocal>
) : Comparator<Language> {

  private val preferredLanguages = localCatalogs
    .groupBy { it.source.lang }
    .mapValues { it.value.size }

  override fun compare(a: Language, b: Language): Int {
    val langOnePosition = preferredLanguages[a.code] ?: 0
    val langTwoPosition = preferredLanguages[b.code] ?: 0

    return langTwoPosition.compareTo(langOnePosition)
  }

}
