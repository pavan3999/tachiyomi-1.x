plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("com.mikepenz.aboutlibraries.plugin")
  id("kotlin-kapt")
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
        implementation(project(Module.uiCore))
        implementation(project(Module.sourceApi))
        implementation(project(Module.domain))
        implementation(libs.aboutLibraries.compose)
        compileOnly(compose.preview)
      }
    }
    named("androidMain") {
      dependencies {
        implementation(androidx.browser)
        implementation(androidx.webkit)
        implementation(androidx.emoji)
        implementation(androidx.compose.navigation)

        implementation(accompanist.pager)
        implementation(accompanist.pagerIndicator)
        implementation(accompanist.flowlayout)
        implementation(accompanist.insets)
        implementation(accompanist.systemUiController)
        implementation(accompanist.swipeRefresh)
        implementation(accompanist.navAnimation)
        implementation(accompanist.navMaterial)

        implementation(libs.coil.core)
        implementation(libs.coil.compose)
      }
    }
    named("desktopMain") {
    }
  }
}

dependencies {
  add("kapt", libs.toothpick.compiler)
}

android {
  namespace = "tachiyomi.ui"
}

idea {
  module {
    (this as ExtensionAware).configure<org.jetbrains.gradle.ext.ModuleSettings> {
      (this as ExtensionAware).configure<org.jetbrains.gradle.ext.PackagePrefixContainer> {
        arrayOf(
          "src/commonMain/kotlin",
          "src/androidMain/kotlin",
          "src/desktopMain/kotlin"
        ).forEach { put(it, "tachiyomi.ui") }
      }
    }
  }
}
