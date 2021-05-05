/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

/*
 * This plugin adds configurations in common between the 'library' projects, such as CLI and Core.
 * It will be applied only into the cited projects.
 */

plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("scalaquest.common-scala-conventions")

    // Apply the java-library plugin for API and implementation separation.
    `java-library`

    // Apply maven-publish and signing, to publish releases to Maven Central.
    `maven-publish`
    signing
}

// Libraries need 75% coverage at least.
scoverage {
    minimumRate.set(0.75.toBigDecimal())
}

// Including sources and javadoc JARs is necessary to publish with Maven.
java {
    withJavadocJar()
    withSourcesJar()
}

// Customizing the manifest file.
tasks.jar {
    manifest {
        attributes(mapOf("Implementation-Title" to project.name,
            "Implementation-Version" to project.version))
    }
}

// Maven-publish plugin configuration. This creates a publication for each
// library, named with the subproject's name.
publishing {
    publications {
        create<MavenPublication>(name) {
            from(components["java"])

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            // Repository credentials are set using environment variable, if available. If not
            // available, credentials are searched into the project properties.
            repositories {
                maven {
                    name = "MavenCentral"
                    url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")

                    credentials.username =
                        System.getenv("MAVEN_CENTRAL_USERNAME")
                            ?: findProperty("mavenCentralUsername").toString()

                    credentials.password =
                        System.getenv("MAVEN_CENTRAL_PASSWORD")
                            ?: findProperty("mavenCentralPassword").toString()
                }
            }

            pom {
                name.set("ScalaQuest ${artifactId.capitalize()}")
                description.set("The ${artifactId.capitalize()} module of the ScalaQuest Project.")
                url.set("https://scalaquest.github.io/PPS-19-ScalaQuest")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/scalaquest/PPS-19-ScalaQuest/blob/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("riccm")
                        name.set("Riccardo Maldini")
                        email.set("riccardo.maldini@gmail.com")
                    }
                    developer {
                        id.set("filin")
                        name.set("Filippo Nardini")
                        email.set("filippo.nardini@studio.unibo.it")
                    }
                    developer {
                        id.set("frang")
                        name.set("Francesco Gorini")
                        email.set("francesco.gorini@studio.unibo.it")
                    }
                    developer {
                        id.set("jacoc")
                        name.set("Jacopo Corina")
                        email.set("jacopo.corina@studio.unibo.it")
                    }
                    developer {
                        id.set("thoma")
                        name.set("Thomas Angelini")
                        email.set("thomas.angelini@studio.unibo.it")
                    }
                }
                scm {
                    connection.set("git:git@github.com:scalaquest/PPS-19-ScalaQuest.git")
                    developerConnection.set("git:ssh://git@github.com:scalaquest/PPS-19-ScalaQuest.git")
                    url.set("https://github.com/scalaquest/PPS-19-ScalaQuest")
                }
            }
        }
    }
}

// Signing configuration. Ascii-armored private key and passphrase are set using environment
// variables, when available. If not available, credentials are searched into the project properties.
signing {
    val signingPassword = System.getenv("SIGNING_PASSWORD")
        ?: findProperty("signingPassword")?.toString()
    val signingKey = System.getenv("SIGNING_KEY")
        ?: findProperty("signingKey")?.toString()
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications[name])
}
