@Suppress("ClassName", "MemberVisibilityCanBePrivate")
object Deps {

  object kotlin {
    const val version = "1.5.30"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"

    object coroutines {
      private const val version = "1.5.1"
      const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
      const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object serialization {
      private const val version = "1.2.2"
      const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:$version"
      const val protobuf = "org.jetbrains.kotlinx:kotlinx-serialization-protobuf:$version"
    }
  }

  object androidx {
    const val core = "androidx.core:core-ktx:1.7.0-alpha02"
    const val appCompat = "androidx.appcompat:appcompat:1.4.0-alpha03"
    const val browser = "androidx.browser:browser:1.3.0"
    const val webkit = "androidx.webkit:webkit:1.4.0"
    const val sqlite = "androidx.sqlite:sqlite-ktx:2.2.0-alpha02"
    const val dataStore = "androidx.datastore:datastore-preferences:1.0.0"
    const val emoji = "androidx.emoji2:emoji2-views:1.0.0-alpha03"

    object compose {
      const val version = "1.1.0-alpha03"
      const val compiler = "androidx.compose.compiler:compiler:$version"
      const val ui = "androidx.compose.ui:ui:$version"
      const val material = "androidx.compose.material:material:$version"
      const val icons = "androidx.compose.material:material-icons-extended:$version"
      const val tooling = "androidx.compose.ui:ui-tooling:$version"
      const val navigation = "androidx.navigation:navigation-compose:2.4.0-alpha08"
    }

    object lifecycle {
      private const val version = "2.4.0-alpha03"
      const val common = "androidx.lifecycle:lifecycle-common-java8:$version"
      const val process = "androidx.lifecycle:lifecycle-process:$version"
      const val extensions = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
    }

    object workManager {
      private const val version = "2.7.0-beta01"
      const val runtime = "androidx.work:work-runtime-ktx:$version"
    }

    object room {
      private const val version = "2.4.0-alpha04"
      const val runtime = "androidx.room:room-runtime:$version"
      const val ktx = "androidx.room:room-ktx:$version"
      const val compiler = "androidx.room:room-compiler:$version"
    }
  }

  object accompanist {
    private const val version = "0.17.0"
    const val pager = "com.google.accompanist:accompanist-pager:$version"
    const val pagerIndicator = "com.google.accompanist:accompanist-pager-indicators:$version"
    const val flowlayout = "com.google.accompanist:accompanist-flowlayout:$version"
    const val insets = "com.google.accompanist:accompanist-insets:$version"
    const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:$version"
    const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
  }

  const val sqlite = "com.github.requery:sqlite-android:3.36.0"

  object toothpick {
    private const val version = "3.1.0"
    const val runtime = "com.github.stephanenicolas.toothpick:toothpick-runtime:$version"
    const val smoothie = "com.github.stephanenicolas.toothpick:smoothie:$version"
    const val compiler = "com.github.stephanenicolas.toothpick:toothpick-compiler:$version"
    const val ktp = "com.github.stephanenicolas.toothpick:ktp:$version"
    const val testing = "com.github.stephanenicolas.toothpick:toothpick-testing-junit5:$version"
  }

  const val okhttp = "com.squareup.okhttp3:okhttp:5.0.0-alpha.2"
  const val quickjs = "app.cash.quickjs:quickjs-android:0.9.1"
  const val jsoup = "org.jsoup:jsoup:1.14.2"

  const val flomo = "io.github.erikhuizinga:flomo:0.1.0-beta"

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

  object aboutLibraries {
    private const val version = "8.9.1"
    const val plugin = "com.mikepenz.aboutlibraries.plugin:aboutlibraries-plugin:$version"
    const val core = "com.mikepenz:aboutlibraries-core:$version"
  }

  const val desugarJdkLibs = "com.android.tools:desugar_jdk_libs:1.1.5"

  const val mockk = "io.mockk:mockk:1.12.0"

  object kotest {
    private const val version = "4.6.1"
    const val framework = "io.kotest:kotest-runner-junit5-jvm:$version"
    const val assertions = "io.kotest:kotest-assertions-core-jvm:$version"
  }

}
