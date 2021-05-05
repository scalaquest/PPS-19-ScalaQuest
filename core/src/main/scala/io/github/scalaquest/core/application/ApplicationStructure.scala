/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.application

import io.github.scalaquest.core.model.Model

/**
 * Mixin used to inherit types defined in a specific model.
 */
trait ApplicationStructure[M0 <: Model] {

  val model: M0

  type M     = model.type
  type S     = model.S
  type I     = model.I
  type RM    = model.RM
  type G     = model.G
  type React = model.Reaction
}
