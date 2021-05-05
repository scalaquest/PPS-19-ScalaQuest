/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.cli

import io.github.scalaquest.core.Game
import io.github.scalaquest.core.model.{Message, Model}

abstract class TestGame[M <: Model](override val model: M) extends Game[M](model) {

  var states: List[model.S] = List()

  def returns(input: String): Either[String, (model.S, Seq[Message])]

  override def send(input: String)(
    state: model.S
  ): Either[String, (model.S, Seq[Message])] = {
    states = states :+ state
    returns(input)
  }
}
