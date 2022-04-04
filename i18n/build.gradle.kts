plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("org.jetbrains.compose")
  id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
  android()
  jvm("desktop")

  sourceSets {
    named("commonMain") {
      dependencies {
        compileOnly(compose.runtime)
        compileOnly(compose.ui)
        api(libs.moko.core)
      }
    }
    named("androidMain") {
    }
    named("desktopMain") {
    }
  }
}

android {
  sourceSets {
    named("main") {
      res.srcDir("src/commonMain/resources")
    }
  }
}

multiplatformResources {
  multiplatformResourcesPackage = "tachiyomi.i18n"
}
