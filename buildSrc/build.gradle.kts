plugins {
    // Support convention plugins written in Kotlin. Convention plugins are
    // build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`

    // Configurable static source code analyzer for Kotlin. Ensures correctness also for the convention plugins.
    id("io.gitlab.arturbosch.detekt")
}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()

    // Configuration-based content filtering for Detekt.
    jcenter {
        content {
            onlyForConfigurations("detekt")
        }
    }
}

dependencies {
    // These dependencies specify versions for the external plugins used inside the convention plugins.
    implementation("org.danilopianini:git-sensitive-semantic-versioning:_")
    implementation("com.github.maiflai:gradle-scalatest:_")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:_")
    implementation("gradle.plugin.org.scoverage:gradle-scoverage:_")

    // Makes Detekt configurable.
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")
}

detekt {
    // Fail build on any finding.
    failFast = true

    // Preconfigure defaults.
    buildUponDefaultConfig = true

    // Custom additional rules.
    config = files("$projectDir/config/detekt.yml")
}
