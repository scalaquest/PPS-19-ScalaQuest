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
}

repositories {
    jcenter()
}

tasks.withType<ScalaCompile> {
    scalaCompileOptions.additionalParameters = listOf(
            "-feature",
            "-language:implicitConversions",
            "-language:higherKinds",
            "-Xfatal-warnings",
            "-Wunused:implicits",
            "-Wunused:imports",
            "-Wunused:locals",
            "-Wunused:params",
            "-Wunused:privates"
    )
}

tasks.named<Test>("test") {
    dependsOn += "scalatest"
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

    // Dependency required by the Maiflai Scalatest plugin to correctly generate HTML test reports.
    // The version is hardcoded, as maiflai.scalatest requires this specific version
    testRuntimeOnly("com.vladsch.flexmark:flexmark-all:0.35.10")
}
