plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-kapt")
}

android {
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
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = Deps.androidx.compose.version
  }
}

dependencies {
  implementationProject(Projects.common)
  implementationProject(Projects.domain)
  implementationProject(Projects.data)
  implementationProject(Projects.presentation)

  implementation(Deps.androidx.core)
  implementation(Deps.androidx.emoji)
  implementation(Deps.androidx.appCompat)
  implementation(Deps.androidx.compose.activity)

  implementation(Deps.toothpick.runtime)
  implementation(Deps.toothpick.smoothie)
  implementation(Deps.toothpick.ktp)
  kapt(Deps.toothpick.compiler)

  implementation(Deps.tinylog.impl)
}
