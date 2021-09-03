plugins {
  id("com.android.library")
  id("kotlin-android")
  id("kotlin-kapt")
}

dependencies {
  coreLibraryDesugaring(Deps.desugarJdkLibs)

  apiProject(Projects.common)

  implementation(Deps.kotlin.coroutines.core)
  implementation(Deps.kotlin.coroutines.android)
  implementation(Deps.androidx.core)
  implementation(Deps.androidx.sqlite)
  implementation(Deps.androidx.dataStore)
  implementation(Deps.androidx.lifecycle.common)
  implementation(Deps.androidx.lifecycle.process)
  implementation(Deps.androidx.lifecycle.extensions)
  implementation(Deps.quickjs)
  implementation(Deps.flomo)

  implementation(Deps.toothpick.runtime)
  implementation(Deps.toothpick.ktp)
  kapt(Deps.toothpick.compiler)
}
