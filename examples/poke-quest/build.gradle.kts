plugins {
    id("scalaquest.examples-conventions")
    application
}

dependencies {
    // the example is based on the Scalaquest Shell version.
    implementation(project(":cli"))
}

application {
    // Define the main class for the application.
    mainClass.set("io.github.scalaquest.examples.pokequest.PokeQuest")
}
