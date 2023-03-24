plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("org.jetbrains.compose")
  id("org.jetbrains.gradle.plugin.idea-ext")
}

kotlin {
  android()
  jvm("desktop")

  sourceSets {
    named("commonMain") {
      dependencies {
        implementation(project(Module.core))
        api(project(Module.i18n))
        api(compose.material)
        api(compose.materialIconsExtended)
        compileOnly(compose.preview)
      }
    }
    named("androidMain") {
      dependencies {
        implementation(androidx.emoji)
        implementation(androidx.lifecycle.viewModel)
        implementation(androidx.compose.activity)
        implementation(accompanist.insets)
        implementation(accompanist.swipeRefresh)
        implementation(accompanist.pager)
        implementation(accompanist.pagerIndicator)
        implementation(libs.coil.compose)
      }
    }
    named("desktopMain") {
      dependencies {
        api(compose.desktop.currentOs)
      }
    }
  }
}

android {
  namespace = "tachiyomi.ui.core"
}

idea {
  module {
    (this as ExtensionAware).configure<org.jetbrains.gradle.ext.ModuleSettings> {
      (this as ExtensionAware).configure<org.jetbrains.gradle.ext.PackagePrefixContainer> {
        arrayOf(
          "src/commonMain/kotlin",
          "src/androidMain/kotlin",
          "src/desktopMain/kotlin"
        ).forEach { put(it, "tachiyomi.ui.core") }
      }
    }
  }
}
