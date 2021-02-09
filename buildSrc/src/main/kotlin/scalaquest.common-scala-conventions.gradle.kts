/*
 * This plugin comprehends configuration in common between all the projects.
 */

plugins {
    // Adds support for Scala
    scala

    // Adds scoverage support
    id("org.scoverage")

    // Support for semantic git-sensitive semantic versioning
    id("org.danilopianini.git-sensitive-semantic-versioning")

    // Plugin used to enable ScalaTest inside Gradle
    id("com.github.maiflai.scalatest")

    // A scala linter-formatter
    id("com.diffplug.spotless")

    id("org.sonarqube")
}

repositories {
    jcenter()
}

sonarqube {
    properties {
        property("sonar.sources", "src/main/scala")
        property("sonar.tests", "src/test/scala")
        property("sonar.junit.reportPaths", "build/test-results/test")
        property("sonar.scala.coverage.reportPath", "build/reports/scoverage/scoverage.xml")
    }
}

tasks.withType<ScalaCompile> {
    scalaCompileOptions.additionalParameters = listOf("-feature", "-language:implicitConversions")
}

group = "io.github.scalaquest"

gitSemVer {
    minimumVersion.set("0.1.0")
    developmentIdentifier.set("dev")
    noTagIdentifier.set("archeo")
    fullHash.set(false)
    maxVersionLength.set(Int.MAX_VALUE)
    developmentCounterLength.set(2)
    version = computeGitSemVer()
}

tasks.register("generateVersionFile") {
    mkdir("build")
    File("$buildDir/version").writeText(version.toString())
}

spotless {
    // scala format with Scalafmt
    scala {
        scalafmt("2.7.5").configFile("${rootDir.absolutePath}/.scalafmt.conf")
    }
}

dependencies {
    // Enables Scala 2.13 Standard Library inside every subproject
    implementation("org.scala-lang:scala-library:_")

    // Lenses
    implementation("com.github.julien-truffaut:monocle-core_2.13:_")
    implementation("com.github.julien-truffaut:monocle-macro_2.13:_")

    // Cats
    implementation("org.typelevel:cats-core_2.13:2.3.1")

    // The ScalaTest framework
    testImplementation("org.scalatest:scalatest_2.13:_")

    // dependency required by the Maiflai Scalatest plugin to correctly generate HTML test reports
    testRuntimeOnly("com.vladsch.flexmark:flexmark-all:_")
}
