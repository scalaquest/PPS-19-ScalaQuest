plugins {
    id("scalaquest.examples-conventions")
    application
}

dependencies {
    // The example is based on the ScalaQuest CLI module.
    implementation(project(":cli"))
}

application {
    // Define the main class for the application.
    mainClass.set("io.github.scalaquest.examples.escaperoom.App")
}
