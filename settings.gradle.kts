include(":core")
include(":domain")
include(":ui-core")
include(":data")
include(":presentation")
include(":app")
include(":source-api")
include(":i18n")

rootProject.name = "Tachiyomi 1.x"

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    google()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven(url = "https://jitpack.io")
  }
  versionCatalogs {
    create("kotlinx") {
      from(files("gradle/kotlinx.versions.toml"))
    }
    create("androidx") {
      from(files("gradle/androidx.versions.toml"))
    }
    create("accompanist") {
      from(files("gradle/accompanist.versions.toml"))
    }
  }
}
