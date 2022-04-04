buildscript {
  dependencies {
    classpath(androidx.agp)
    classpath(kotlinx.gradle)
    classpath(kotlinx.serialization.gradle)
    classpath(androidx.compose.gradle)
    classpath(libs.aboutLibraries.gradle)
    classpath(libs.moko.gradle)
    classpath(libs.sqldelight.gradle)
  }
}

plugins {
  alias(libs.plugins.gradleVersions)
  alias(libs.plugins.ideaExt)
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
        add("coreLibraryDesugaring", libs.desugarJdkLibs)
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

tasks.register<Delete>("clean") {
  delete(rootProject.buildDir)
}
