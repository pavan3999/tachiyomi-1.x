plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("kotlin-kapt")
  id("org.jetbrains.gradle.plugin.idea-ext")
  `maven-publish`
  signing
}

kotlin {
  android()
  jvm("desktop")

  sourceSets {
    named("commonMain") {
      dependencies {
        api(kotlinx.coroutines.core)
        api(kotlinx.stdlib)
        api(kotlinx.datetime)
        api(kotlinx.serialization.json)
        api(libs.ktor.core)
        api(libs.ktor.contentnegotiation)
        api(libs.ktor.serialization)
        api(libs.okio)
        api(libs.toothpick.runtime)
      }
    }
    named("androidMain") {
      kotlin.srcDir("src/jvmMain/kotlin")
      dependencies {
        implementation(androidx.core)
        implementation(androidx.lifecycle.process)
        implementation(androidx.dataStore)
        implementation(libs.quickjs.android)
        api(libs.ktor.okhttp)
        implementation(libs.bundles.tinylog)
      }
    }
    named("desktopMain") {
      kotlin.srcDir("src/jvmMain/kotlin")
      dependencies {
        implementation(libs.quickjs.jvm)
        api(libs.ktor.okhttp)
        implementation(libs.bundles.tinylog)
      }
    }
  }
}

dependencies {
  add("kapt", libs.toothpick.compiler)
}

android {
  namespace = "tachiyomi.core"
}

afterEvaluate {
  configure<PublishingExtension> {
    publications.all {
      val mavenPublication = this as? MavenPublication
      mavenPublication?.artifactId = "${project.name}-$name"
    }
  }
}

val packageVersion = "1.2-SNAPSHOT"

publishing {
  publications.withType(MavenPublication::class) {
    groupId = "org.tachiyomi"
    artifactId = "core"
    version = packageVersion
    pom {
      name.set("Tachiyomi Core")
      description.set("Common classes for Tachiyomi.")
      url.set("https://github.com/tachiyomiorg/tachiyomi-1.x")
      licenses {
        license {
          name.set("Mozilla Public License 2.0")
          url.set("https://www.mozilla.org/en-US/MPL/2.0/")
        }
      }
      developers {
        developer {
          id.set("inorichi")
          name.set("Javier Tomás")
          email.set("len@kanade.eu")
        }
      }
      scm {
        connection.set("scm:git:git:github.com:tachiyomiorg/tachiyomi-1.x.git")
        developerConnection.set("scm:git:github.com:tachiyomiorg/tachiyomi-1.x.git")
        url.set("https://github.com/tachiyomiorg/tachiyomi-1.x")
      }
    }
  }

  repositories {
    maven {
      val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2"
      val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots"
      setUrl(if (packageVersion.endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)

      credentials {
        username = System.getenv("MAVEN_USERNAME")
        password = System.getenv("MAVEN_PASSWORD")
      }
    }
  }
}

signing {
  sign(publishing.publications)
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
        ).forEach { put(it, "tachiyomi.core") }
      }
    }
  }
}
