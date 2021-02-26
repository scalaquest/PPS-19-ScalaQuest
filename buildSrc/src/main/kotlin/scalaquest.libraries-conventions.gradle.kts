/*
 * This plugin comprehends configuration in common between the 'library' projects, such as cli and core.
 * It will be applied only into the cited projects.
 */

plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("scalaquest.common-scala-conventions")

    // Apply the java-library plugin for API and implementation separation.
    `java-library`

    `maven-publish`
    signing
}

// libraries need 75% coverage at least
scoverage {
    minimumRate.set(0.75.toBigDecimal())
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.jar {
    manifest {
        attributes(mapOf("Implementation-Title" to project.name,
            "Implementation-Version" to project.version))
    }
}

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

signing {
    val signingPassword = System.getenv("SIGNING_PASSWORD")
        ?: findProperty("signingPassword")?.toString()
    val signingKey = System.getenv("SIGNING_KEY")
        ?: findProperty("signingKey")?.toString()
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications[name])
}
