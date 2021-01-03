/*
 * This plugin comprehends configuration in common between all the projects.
 */

plugins {
    // Adds support for Scala
    scala

    // Support for semantic gis-sensitive semantic versioning
    id("org.danilopianini.git-sensitive-semantic-versioning")

    // Plugin used to enable ScalaTest inside Gradle
    id("com.github.maiflai.scalatest")

    // A scala linter-formatter
    id("com.diffplug.spotless")
}

repositories {
    jcenter()
}


tasks.withType<ScalaCompile> {
    scalaCompileOptions.additionalParameters = listOf("-feature", "-language:implicitConversions")
}

group = "io.github.scalaquest"

gitSemVer {
    version = computeGitSemVer()
}

spotless {
    scala {
        // by default, all `.scala` and `.sc` files in the java sourcesets will be formatted

        scalafmt() // has its own section below
    }
}

dependencies {
    // Enables Scala 2.13 Standard Library inside every subproject
    implementation("org.scala-lang:scala-library:_")

    // Lenses
    implementation("com.github.julien-truffaut:monocle-core_2.13:2.0.3")
    implementation("com.github.julien-truffaut:monocle-macro_2.13:2.0.3")

    // The ScalaTest framework
    testImplementation("org.scalatest:scalatest_2.13:_")

    // dependency required by the Maiflai Scalatest plugin to correctly generate HTML test reports
    testRuntimeOnly("com.vladsch.flexmark:flexmark-all:_")
}
