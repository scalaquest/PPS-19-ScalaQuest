plugins {
    id("scalaquest.examples-conventions")
    application
}

dependencies {
    // the example is based on the ScalaQuest Cli module.
    implementation(project(":cli"))
}

application {
    // Define the main class for the application.
    mainClass.set("io.github.scalaquest.examples.escaperoom.EscapeRoom")
}
