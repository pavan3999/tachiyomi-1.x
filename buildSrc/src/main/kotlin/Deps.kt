@Suppress("ClassName", "MemberVisibilityCanBePrivate")
object Deps {

  object kotlin {
    const val version = "1.5.31"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-common:$version"
    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.3.0"
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

    object coroutines {
      private const val version = "1.5.2"
      const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
      const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object serialization {
      private const val version = "1.3.0"
      const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:$version"
      const val protobuf = "org.jetbrains.kotlinx:kotlinx-serialization-protobuf:$version"
      const val plugin = "org.jetbrains.kotlin:kotlin-serialization:${kotlin.version}"
    }
  }

  object androidx {
    const val core = "androidx.core:core-ktx:1.7.0-beta02"
    const val appCompat = "androidx.appcompat:appcompat:1.4.0-beta01"
    const val browser = "androidx.browser:browser:1.4.0-beta01"
    const val webkit = "androidx.webkit:webkit:1.4.0"
    const val sqlite = "androidx.sqlite:sqlite-ktx:2.2.0-alpha02"
    const val dataStore = "androidx.datastore:datastore-preferences:1.0.0"
    const val emoji = "androidx.emoji2:emoji2-views:1.0.0-beta01"

    object compose {
      const val activity = "androidx.activity:activity-compose:1.4.0-beta01"
      const val navigation = "androidx.navigation:navigation-compose:2.4.0-alpha10"
      const val plugin = "org.jetbrains.compose:compose-gradle-plugin:1.0.0-alpha4-build385"
    }

    object lifecycle {
      private const val version = "2.4.0-rc01"
      const val common = "androidx.lifecycle:lifecycle-common-java8:$version"
      const val process = "androidx.lifecycle:lifecycle-process:$version"
      const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
    }

    object workManager {
      private const val version = "2.7.0-rc01"
      const val runtime = "androidx.work:work-runtime-ktx:$version"
    }
  }

  object accompanist {
    private const val version = "0.19.0"
    const val pager = "com.google.accompanist:accompanist-pager:$version"
    const val pagerIndicator = "com.google.accompanist:accompanist-pager-indicators:$version"
    const val flowlayout = "com.google.accompanist:accompanist-flowlayout:$version"
    const val insets = "com.google.accompanist:accompanist-insets:$version"
    const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:$version"
    const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
    const val navAnimation = "com.google.accompanist:accompanist-navigation-animation:$version"
  }

  object sqldelight {
    private const val version = "1.5.1"
    const val runtime = "com.squareup.sqldelight:runtime:$version"
    const val coroutines = "com.squareup.sqldelight:coroutines-extensions:$version"
    const val jvm = "com.squareup.sqldelight:sqlite-driver:$version"
    const val android = "com.squareup.sqldelight:android-driver:$version"
    const val plugin = "com.squareup.sqldelight:gradle-plugin:$version"
  }

  const val requerySqlite = "com.github.requery:sqlite-android:3.36.0"
  const val androidSqlite = "androidx.sqlite:sqlite-framework:2.2.0-alpha02"

  object toothpick {
    private const val version = "3.1.0"
    const val runtime = "com.github.stephanenicolas.toothpick:ktp:$version"
    const val smoothie = "com.github.stephanenicolas.toothpick:smoothie:$version"
    const val compiler = "com.github.stephanenicolas.toothpick:toothpick-compiler:$version"
    const val testing = "com.github.stephanenicolas.toothpick:toothpick-testing-junit5:$version"
  }

  object ktor {
    private const val version = "1.6.4"
    const val core = "io.ktor:ktor-client-core:$version"
    const val okhttp = "io.ktor:ktor-client-okhttp:$version"
    const val serialization = "io.ktor:ktor-client-serialization:$version"
  }

  const val okio = "com.squareup.okio:okio:3.0.0-alpha.10"
  const val quickjsAndroid = "app.cash.quickjs:quickjs-android:0.9.2"
  const val quickjsJvm = "app.cash.quickjs:quickjs-jvm:0.9.2"
  const val jsoup = "org.jsoup:jsoup:1.14.3"

  object tinylog {
    private const val version = "2.3.2"
    const val impl = "org.tinylog:tinylog-impl:$version"
    const val api = "org.tinylog:tinylog-api:$version"
  }

  object coil {
    private const val version = "1.3.2"
    const val core = "io.coil-kt:coil:$version"
    const val compose = "io.coil-kt:coil-compose:$version"
  }

  object moko {
    private const val version = "0.17.2"
    const val core = "dev.icerock.moko:resources:$version"
    const val plugin = "dev.icerock.moko:resources-generator:$version"
  }

  object aboutLibraries {
    private const val version = "8.9.1"
    const val plugin = "com.mikepenz.aboutlibraries.plugin:aboutlibraries-plugin:$version"
    const val core = "com.mikepenz:aboutlibraries-core:$version"
  }

  const val desugarJdkLibs = "com.android.tools:desugar_jdk_libs:1.1.5"

  const val mockk = "io.mockk:mockk:1.12.0"

  object kotest {
    private const val version = "4.6.3"
    const val framework = "io.kotest:kotest-runner-junit5-jvm:$version"
    const val assertions = "io.kotest:kotest-assertions-core-jvm:$version"
  }

}
