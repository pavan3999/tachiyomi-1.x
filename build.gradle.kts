buildscript {
  repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
  dependencies {
    classpath("com.android.tools.build:gradle:7.0.3")
    classpath(Deps.kotlin.plugin)
    classpath(Deps.kotlin.serialization.plugin)
    classpath(Deps.androidx.compose.plugin)
    classpath(Deps.aboutLibraries.plugin)
    classpath(Deps.moko.plugin)
    classpath(Deps.sqldelight.plugin)
  }
}

plugins {
  id("com.github.ben-manes.versions") version "0.39.0"
  id("org.jetbrains.gradle.plugin.idea-ext") version "1.1"
}

allprojects {
  repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven { setUrl("https://jitpack.io") }
  }
}

subprojects {
  tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_1_8.toString()
      freeCompilerArgs = freeCompilerArgs + listOf(
        "-Xjvm-default=compatibility",
      )
    }
  }
  tasks.withType<Test> {
    useJUnitPlatform()
  }

  plugins.withType<org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper> {
    configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
      sourceSets.all {
        languageSettings {
          optIn("kotlin.RequiresOptIn")
          optIn("kotlin.ExperimentalStdlibApi")
          optIn("kotlin.time.ExperimentalTime")
          optIn("kotlinx.coroutines.FlowPreview")
          optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
          optIn("kotlinx.coroutines.ObsoleteCoroutinesApi")
          optIn("kotlinx.serialization.ExperimentalSerializationApi")
          optIn("androidx.compose.foundation.ExperimentalFoundationApi")
          optIn("com.google.accompanist.pager.ExperimentalPagerApi")
          optIn("io.ktor.util.InternalAPI")
        }
      }
    }
  }

  plugins.withType<com.android.build.gradle.BasePlugin> {
    configure<com.android.build.gradle.BaseExtension> {
      compileSdkVersion(Config.compileSdk)
      defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode(Config.versionCode)
        versionName(Config.versionName)
        ndk {
          version = Config.ndk
        }
      }
      compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
      }
      sourceSets {
        named("main") {
          val altManifest = file("src/androidMain/AndroidManifest.xml")
          if (altManifest.exists()) {
            manifest.srcFile(altManifest.path)
          }
        }
      }
      dependencies {
        add("coreLibraryDesugaring", Deps.desugarJdkLibs)
      }
    }
  }

  plugins.withType<JacocoPlugin> {
    configure<JacocoPluginExtension> {
      toolVersion = "0.8.7"
    }
  }

  afterEvaluate {
    tasks.withType<JacocoReport> {
      reports {
        xml.required.set(true)
        html.required.set(false)
      }
    }
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}
