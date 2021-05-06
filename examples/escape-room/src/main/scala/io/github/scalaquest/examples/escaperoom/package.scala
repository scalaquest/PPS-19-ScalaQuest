/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples

import io.github.scalaquest.core.application.ApplicationStructure
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel

package object escaperoom extends ApplicationStructure[SimpleModel.type] {
  override val model: SimpleModel.type = SimpleModel
}
