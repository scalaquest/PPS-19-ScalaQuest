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
        property("sonar.sources", "src/main/scala")
        property("sonar.tests", "src/test/scala")
        property("sonar.junit.reportPaths", "build/test-results/test")
        property("sonar.scala.version", "2.13")
        property("sonar.scala.scoverage.reportPath", "build/reports/scoverage/scoverage.xml")
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