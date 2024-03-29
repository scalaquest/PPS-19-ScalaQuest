/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

plugins {
    // Root-level plugin is necessary to run aggregateScoverage.
    id("org.scoverage")

    // SonarCloud analysis configuration.
    id("org.sonarqube")
}


sonarqube {
    properties {
        property("sonar.projectKey", "scalaquest_PPS-19-ScalaQuest")
        property("sonar.organization", "scalaquest")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.scala.version", "2.13")

        property("sonar.sources", "src/main/scala")
        property("sonar.tests", "src/test/scala")
        property("sonar.junit.reportPaths", "build/test-results/test")
        property("sonar.scala.coverage.reportPaths", "build/reports/scoverage/scoverage.xml")
    }
}

// Configures sonarqube to be executed after test and coverage reports generation.
project.tasks["sonarqube"]?.dependsOn("test", "reportScoverage")

buildscript { repositories { mavenCentral() }}

repositories {
    jcenter()
}

dependencies {
    // Used by Scoverage to detect Scala Standard Library version, being root-level.
    implementation("org.scala-lang:scala-library:_")
}