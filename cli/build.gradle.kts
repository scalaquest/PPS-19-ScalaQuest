/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

plugins {
    id("scalaquest.libraries-conventions")
}

dependencies {
    // The library comprehends the Core module of the project.
    api(project(":core"))

    // Enables ZIO test framework.
    api("dev.zio:zio_2.13:_")
    testImplementation("dev.zio:zio-test_2.13:_")
    testImplementation("dev.zio:zio-test-junit_2.13:_")
}
