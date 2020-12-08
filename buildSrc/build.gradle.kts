plugins {
    // Support convention plugins written in Kotlin. Convention plugins are
    // build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`

    // Configurable static source code analyzer for Kotlin. Ensures correctness also for the convention plugins
    id("io.gitlab.arturbosch.detekt")
}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()

    // configuration-based content filtering for Detekt
    jcenter {
        content {
            onlyForConfigurations("detekt")
        }
    }
}

dependencies {
    //These implementation dependencies specifies versions for the external plugins
    // used inside the project convention plugins (i.e. common, examples, libraries)
    implementation("org.danilopianini:git-sensitive-semantic-versioning:_")
    implementation("org.danilopianini:publish-on-central:_")
    implementation("gradle.plugin.com.github.maiflai:gradle-scalatest:_")

    // Makes Detekt configurable
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")
}

detekt {
    // fail build on any finding
    failFast = true

    // preconfigure defaults
    buildUponDefaultConfig = true

    // Custom additional rules
    config = files("$projectDir/config/detekt.yml")
}
