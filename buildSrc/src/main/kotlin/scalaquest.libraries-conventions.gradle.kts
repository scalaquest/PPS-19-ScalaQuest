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
