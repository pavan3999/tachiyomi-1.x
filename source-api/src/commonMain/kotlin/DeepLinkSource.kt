package tachiyomi.source

import tachiyomi.source.model.DeepLink

interface DeepLinkSource : Source {

  fun handleLink(url: String): DeepLink?

  fun findMangaKey(chapterKey: String): String?

}
