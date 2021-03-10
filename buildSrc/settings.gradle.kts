import de.fayard.refreshVersions.bootstrapRefreshVersionsForBuildSrc
import de.fayard.refreshVersions.migrateRefreshVersionsIfNeeded

buildscript {
    // Makes the refreshVersions plugin work also for the convention plugins.
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

bootstrapRefreshVersionsForBuildSrc()
