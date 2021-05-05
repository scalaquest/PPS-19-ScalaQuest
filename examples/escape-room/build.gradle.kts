/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

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
