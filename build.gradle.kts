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
