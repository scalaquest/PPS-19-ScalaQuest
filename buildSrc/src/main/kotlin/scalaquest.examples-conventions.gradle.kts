/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

/*
 * This plugin adds configuration in common between the 'examples' projects, that are Scala
 * applications, such as escape-room.
 */

plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("scalaquest.common-scala-conventions")

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

// The coverage threshold into the examples is not mandatory.
scoverage {
    minimumRate.set(0.toBigDecimal())
}

// Enables the possibility for the application to be interactive.
tasks.withType<JavaExec> {
    standardInput = System.`in`
}
