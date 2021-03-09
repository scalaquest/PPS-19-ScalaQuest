plugins {
    id("scalaquest.examples-conventions")
    application
}

dependencies {
    // the example is based on the Scalaquest Shell version, imported from Maven Central.
    implementation("io.github.scalaquest:cli:_")
}

application {
    // Define the main class for the application.
    mainClass.set("io.github.scalaquest.examples.wizardquest.App")
}
