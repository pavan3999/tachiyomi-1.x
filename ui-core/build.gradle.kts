plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("org.jetbrains.compose")
  id("org.jetbrains.gradle.plugin.idea-ext")
}

kotlin {
  jvm()
  android()

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
    named("jvmMain") {
      dependencies {
        api(compose.desktop.currentOs)
      }
    }
    named("androidMain") {
      dependencies {
        implementation(Deps.androidx.emoji)
        implementation(Deps.toothpick.runtime)
        implementation(Deps.androidx.lifecycle.viewModel)
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
          "src/androidMain/kotlin"
        ).forEach { put(it, "tachiyomi.ui.core") }
      }
    }
  }
}
