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
  jvm()

  sourceSets {
    named("commonMain") {
      dependencies {
        implementation(project(Module.core))
        implementation(project(Module.uiCore))
        compileOnly(compose.preview)
      }
    }
    named("jvmMain") {
    }
    named("androidMain") {
      kotlin.srcDir("src/sharedJvmMain/kotlin")
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

        implementation(Deps.coil.core)
        implementation(Deps.coil.compose)

        implementation(Deps.aboutLibraries.core)
      }
    }
    listOf("jvmMain", "androidMain").forEach {
      getByName(it) {
        dependencies {
          implementation(project(Module.sourceApi))
          implementation(project(Module.domain))
          implementation(Deps.toothpick.runtime)
        }
        project.dependencies.apply {
          add("kapt", Deps.toothpick.compiler)
        }
      }
    }
  }
}

idea {
  module {
    (this as ExtensionAware).configure<org.jetbrains.gradle.ext.ModuleSettings> {
      (this as ExtensionAware).configure<org.jetbrains.gradle.ext.PackagePrefixContainer> {
        arrayOf(
          "src/commonMain/kotlin",
          "src/jvmMain/kotlin",
          "src/androidMain/kotlin",
          "src/sharedJvmMain/kotlin"
        ).forEach { put(it, "tachiyomi.ui") }
      }
    }
  }
}
