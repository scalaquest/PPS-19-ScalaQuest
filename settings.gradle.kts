import de.fayard.refreshVersions.bootstrapRefreshVersions
import de.fayard.refreshVersions.migrateRefreshVersionsIfNeeded
buildscript {
    repositories {
        gradlePluginPortal()
        maven("https://dl.bintray.com/jmfayard/maven")
    }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.7")
////                                                      # available:0.9.8-dev-001")
////                                                      # available:0.9.8-dev-002")
////                                                      # available:0.9.8-dev-003")
////                                                      # available:0.9.8-dev-004")
}
migrateRefreshVersionsIfNeeded("0.9.7") // Will be automatically removed by refreshVersions when upgraded to the latest version.

bootstrapRefreshVersions()

rootProject.name = "ScalaQuest"
include(":core")
include(":cli")
include(":examples:escape-room")
