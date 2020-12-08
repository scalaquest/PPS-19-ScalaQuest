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
}

repositories {
    jcenter()
}

group = "io.github.scalaquest"

gitSemVer {
    version = computeGitSemVer()
}

dependencies {
    // Enables Scala 2.13 Standard Library inside every subproject
    implementation("org.scala-lang:scala-library:_")

    // The ScalaTest framework
    testImplementation("org.scalatest:scalatest_2.13:_")

    // dependency required by the Maiflai Scalatest plugin to correctly generate HTML test reports
    testRuntimeOnly("com.vladsch.flexmark:flexmark-all:_")
}
