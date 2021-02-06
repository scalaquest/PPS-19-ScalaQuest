plugins {
    // Root-level plugin is necessary to run aggregateScoverage
    id("org.scoverage")
}

subprojects {
}

buildscript { repositories { mavenCentral() }}

repositories {
    jcenter()
}

dependencies {
    // Used by Scoverage to detect Scala Standard Library version, being root-level
    implementation("org.scala-lang:scala-library:_")
}