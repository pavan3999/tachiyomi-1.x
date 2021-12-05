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
        implementation(Deps.aboutLibraries.compose)
        compileOnly(compose.preview)
      }
    }
    named("androidMain") {
      dependencies {
        implementation(Deps.androidx.browser)
        implementation(Deps.androidx.webkit)
        implementation(Deps.androidx.emoji)
        implementation(Deps.androidx.compose.navigation)

        implementation(Deps.accompanist.pager)
        implementation(Deps.accompanist.pagerIndicator)
        implementation(Deps.accompanist.flowlayout)
        implementation(Deps.accompanist.insets)
        implementation(Deps.accompanist.systemUiController)
        implementation(Deps.accompanist.swipeRefresh)
        implementation(Deps.accompanist.navAnimation)
        implementation(Deps.accompanist.navMaterial)

        implementation(Deps.coil.core)
        implementation(Deps.coil.compose)
      }
    }
    named("desktopMain") {
    }
  }
}

dependencies {
  add("kapt", Deps.toothpick.compiler)
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
