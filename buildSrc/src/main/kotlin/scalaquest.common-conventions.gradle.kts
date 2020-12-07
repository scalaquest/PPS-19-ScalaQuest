/*
 * This plugin comprehends configuration in common between all the projects.
 */

plugins {
    // Apply the scala Plugin to add support for Scala.
    scala

    // NOTE: external plugin version is specified in implementation dependency artifact of the project's build file.
    id("org.danilopianini.git-sensitive-semantic-versioning")

    // todo Maven Central deploy?
    // `maven-publish`
    // `signing`
    // id("org.danilopianini.publish-on-central")
}

repositories {
    jcenter()
}

group = "io.github.scalaquest"

gitSemVer {
    version = computeGitSemVer()
}

/*
// todo Maven Central Deploy configuration?
publishOnCentral {
    projectDescription.set("description")
    projectLongName.set("full project name")
    licenseName.set("your license") // Defaults to "Apache License, Version 2.0"
    licenseUrl.set("link to your license") // Defaults to http://www.apache.org/licenses/LICENSE-2.0
    projectUrl.set("website url")
    scmConnection.set("git:git@github.com:youruser/yourrepo")
}

publishing {
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "myOrgPrivateRepo"
            url = uri("build/my-repo")
        }
    }
}
*/

dependencies {
    // Use Scala 2.13 library in our project
    implementation("org.scala-lang:scala-library:_")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:_")

    // https://mvnrepository.com/artifact/org.scalatest/scalatest
    testImplementation("org.scalatest:scalatest_2.13:_")
    testImplementation("org.scalatestplus:scalatestplus-junit_2.13:1.0.0-M2")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}
