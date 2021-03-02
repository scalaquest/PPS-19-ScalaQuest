/*
 * This plugin comprehends configuration in common between the 'library' projects, such as cli and core.
 * It will be applied only into the cited projects.
 */

plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("scalaquest.common-scala-conventions")

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

dependencies {
    implementation("com.lihaoyi:upickle_2.13:1.2.3")
}
// libraries need 75% coverage at least
scoverage {
    minimumRate.set(0.75.toBigDecimal())
}
