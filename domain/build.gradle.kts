plugins {
  kotlin("multiplatform")
  id("kotlin-kapt")
  id("kotlinx-serialization")
  id("com.android.library")
  jacoco
  id("org.jetbrains.gradle.plugin.idea-ext")
}

kotlin {
  jvm()

  // Android is required for Toothpick's factories to be bundled. This could probably be removed
  // if/when there's a KSP alternative.
  android()

  sourceSets {
    named("commonMain") {
      dependencies {
        implementation(project(Module.core))
        implementation(project(Module.sourceApi))
        implementation(kotlinx.serialization.protobuf)
      }
    }
    named("jvmMain") {
    }
    named("androidMain") {
    }
    named("jvmTest") {
      dependencies {
        implementation(kotlinx.reflect)
        implementation(libs.mockk)
        implementation(libs.toothpick.testing)
        implementation(libs.bundles.kotest)
      }
    }
  }
}

dependencies {
  add("kapt", libs.toothpick.compiler)
}

val jacocoTestReport by tasks.creating(JacocoReport::class.java) {
  dependsOn("jvmTest")

  val coverageSourceDirs = arrayOf(
    "src/commonMain",
    "src/jvmMain"
  )

  sourceDirectories.setFrom(files(coverageSourceDirs))
  classDirectories.setFrom(files("${buildDir}/classes/kotlin/jvm/"))
  executionData.setFrom(files("${buildDir}/jacoco/jvmTest.exec"))
}

idea {
  module {
    (this as ExtensionAware).configure<org.jetbrains.gradle.ext.ModuleSettings> {
      (this as ExtensionAware).configure<org.jetbrains.gradle.ext.PackagePrefixContainer> {
        arrayOf(
          "src/commonMain/kotlin",
          "src/jvmTest/kotlin"
        ).forEach { put(it, "tachiyomi.domain") }
      }
    }
  }
}
