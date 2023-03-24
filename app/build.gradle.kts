plugins {
  kotlin("multiplatform")
  id("com.android.application")
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
        implementation(project(Module.domain))
        implementation(project(Module.data))
        implementation(project(Module.presentation))
      }
    }
    named("androidMain") {
      dependencies {
        implementation(androidx.core)
        implementation(androidx.emoji)
        implementation(androidx.appCompat)
        implementation(androidx.compose.activity)
        implementation(libs.toothpick.smoothie)
        implementation(libs.tinylog.impl)
      }
    }
    named("desktopMain") {
      dependencies {
        implementation(libs.tinylog.impl)
      }
    }
  }
}

dependencies {
  add("kapt", libs.toothpick.compiler)
}

android {
  namespace = "tachiyomi.app"
  defaultConfig {
    applicationId = Config.applicationId
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFile(getDefaultProguardFile("proguard-android.txt"))
      proguardFile(file("proguard-rules.pro"))
    }
  }
}

compose.desktop {
  application {
    mainClass = "tachiyomi.app.AppKt"
  }
}

idea {
  module {
    (this as ExtensionAware).configure<org.jetbrains.gradle.ext.ModuleSettings> {
      (this as ExtensionAware).configure<org.jetbrains.gradle.ext.PackagePrefixContainer> {
        arrayOf(
          "src/commonMain/kotlin",
          "src/androidMain/kotlin",
          "src/desktopMain/kotlin"
        ).forEach { put(it, "tachiyomi.app") }
      }
    }
  }
}
