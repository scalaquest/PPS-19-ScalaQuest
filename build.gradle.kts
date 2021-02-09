plugins {
    // Root-level plugin is necessary to run aggregateScoverage
    id("org.scoverage")

    // SonarCloud analysis configuration
    id("org.sonarqube")
}


sonarqube {
    properties {
        property("sonar.projectKey", "scalaquest_PPS-19-ScalaQuest")
        property("sonar.organization", "scalaquest")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.scala.version", "2.13")
    }
}

subprojects { }

buildscript { repositories { mavenCentral() }}

repositories {
    jcenter()
}

dependencies {
    // Used by Scoverage to detect Scala Standard Library version, being root-level
    implementation("org.scala-lang:scala-library:_")
}