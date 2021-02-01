/*
 * This plugin comprehends configuration in common between all the projects.
 */

plugins {
    // Adds support for Scala
    scala

    // Adds jacoco support
    jacoco

    // Support for semantic gis-sensitive semantic versioning
    id("org.danilopianini.git-sensitive-semantic-versioning")

    // Plugin used to enable ScalaTest inside Gradle
    id("com.github.maiflai.scalatest")

    // A scala linter-formatter
    id("com.diffplug.spotless")
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                counter = "CLASS"
                value = "COVEREDRATIO"
                minimum = 0.6.toBigDecimal()
            }
        }
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report

    reports {
        xml.isEnabled = true
    }
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
    // scala format with Scalafmt
    scala {
        scalafmt("2.7.5").configFile(rootDir.absolutePath + "/.scalafmt.conf")
    }
}

dependencies {
    // Enables Scala 2.13 Standard Library inside every subproject
    implementation("org.scala-lang:scala-library:_")

    // Lenses
    implementation("com.github.julien-truffaut:monocle-core_2.13:_")
    implementation("com.github.julien-truffaut:monocle-macro_2.13:_")

    // The ScalaTest framework
    testImplementation("org.scalatest:scalatest_2.13:_")

    // dependency required by the Maiflai Scalatest plugin to correctly generate HTML test reports
    testRuntimeOnly("com.vladsch.flexmark:flexmark-all:_")
}
