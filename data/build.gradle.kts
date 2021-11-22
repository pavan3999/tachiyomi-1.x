plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("kotlin-kapt")
  id("kotlinx-serialization")
  id("com.squareup.sqldelight")
  id("org.jetbrains.gradle.plugin.idea-ext")
}

kotlin {
  android()
  jvm("desktop")

  sourceSets {
    named("commonMain") {
      dependencies {
        implementation(project(Module.core))
        implementation(project(Module.domain))
        implementation(project(Module.sourceApi))
        implementation(Deps.sqldelight.runtime)
        implementation(Deps.sqldelight.coroutines)
      }
    }
    named("androidMain") {
      kotlin.srcDir("src/jvmMain/kotlin")
      dependencies {
        implementation(Deps.sqldelight.android)
        implementation(Deps.requerySqlite)
        implementation(Deps.androidx.workManager.runtime)
      }
      project.dependencies {
        compileOnly(Deps.androidSqlite)
        debugImplementation(Deps.androidSqlite)
      }
    }
    named("desktopMain") {
      kotlin.srcDir("src/jvmMain/kotlin")
      dependencies {
        implementation(Deps.sqldelight.jvm)
      }
    }
  }
}

dependencies {
  add("kapt", Deps.toothpick.compiler)
}

sqldelight {
  database("Database") {
    packageName = "tachiyomi.data"
    dialect = "sqlite:3.24"
  }
}

idea {
  module {
    (this as ExtensionAware).configure<org.jetbrains.gradle.ext.ModuleSettings> {
      (this as ExtensionAware).configure<org.jetbrains.gradle.ext.PackagePrefixContainer> {
        arrayOf(
          "src/commonMain/kotlin",
          "src/androidMain/kotlin",
          "src/desktopMain/kotlin",
          "src/jvmMain/kotlin"
        ).forEach { put(it, "tachiyomi.data") }
      }
    }
  }
}
