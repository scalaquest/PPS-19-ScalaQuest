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
    // Adds tuProlog as a dependency.
    implementation("it.unibo.alice.tuprolog:tuprolog:_")
}
