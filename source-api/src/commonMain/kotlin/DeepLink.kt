package tachiyomi.source

sealed class DeepLink {
  abstract val key: String

  data class Manga(override val key: String) : DeepLink()
  data class Chapter(override val key: String) : DeepLink()
}