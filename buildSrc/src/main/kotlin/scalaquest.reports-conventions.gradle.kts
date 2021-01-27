/*
 * This plugin comprehends configuration in common between all the projects.
 */

plugins {
    // A scala linter-formatter
    id("com.diffplug.spotless")
}

spotless {
    // markdown format with Prettier
    format("styling") {
        target("**/*.md")

        // auto format markdown with to 80 characters
        prettier().configFile(rootDir.absolutePath + "/.prettierrc.yml")
    }
}
