plugins {
    id("scalaquest.examples-conventions")
    application
}

dependencies {
    // The example is based on the Scalaquest CLI module, imported from
    // Maven Central, in order to show and test the mechanism.
    implementation("io.github.scalaquest:cli:_")
}

application {
    // Define the main class for the application.
    mainClass.set("io.github.scalaquest.examples.wizardquest.App")
}
