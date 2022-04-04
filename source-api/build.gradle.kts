import org.gradle.api.publish.PublishingExtension

plugins {
  kotlin("multiplatform")
  `maven-publish`
  signing
}

kotlin {
  jvm()

  sourceSets {
    named("commonMain") {
      dependencies {
        implementation(project(Module.core))
      }
    }
    named("jvmMain") {
      dependencies {
        api(libs.jsoup)
      }
    }
  }
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
    artifactId = "source-api"
    version = packageVersion
    pom {
      name.set("Tachiyomi Source API")
      description.set("Core source API for Tachiyomi.")
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
