plugins {
    // Support convention plugins written in Kotlin. Convention plugins are
    // build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`
    id("io.gitlab.arturbosch.detekt")
}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()
    jcenter { content { onlyForConfigurations("detekt") } } // configuration-based content filtering
}

dependencies {
    implementation("org.danilopianini:git-sensitive-semantic-versioning:_")
    implementation("org.danilopianini:publish-on-central:_")

    // Adds a configuration "detektPlugins". The version number must be hardwired, as
    // won't be recognized by the refreshVersions plugin
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.15.0-RC1")
}

detekt {
    failFast = true // fail build on any finding
    buildUponDefaultConfig = true // preconfigure defaults
    config = files("$projectDir/config/detekt.yml") // Custom additional rules
}
