/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.cli

import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.model.{Message, RoomRef}

object CLITestHelper {

  val model: SimpleModel.type = SimpleModel

  def State: SimpleModel.State.type = SimpleModel.State
  type State = SimpleModel.SimpleState

  def state: State =
    State(
      actions = Map.empty,
      rooms = Map.empty,
      items = Map.empty,
      location = RoomRef("1")
    )

  def gameRight: TestGame[SimpleModel.type] =
    new TestGame(model) {

      override def returns(input: String): Either[String, (SimpleModel.SimpleState, Seq[Message])] =
        if (input == "end") Right(state.copy(ended = true) -> Seq())
        else Right(state.copy(_location = RoomRef("2"))    -> Seq(testMessage2))
    }

  def gameLeft: TestGame[SimpleModel.type] =
    new TestGame(model) {

      override def returns(input: String): Either[String, (SimpleModel.SimpleState, Seq[Message])] =
        if (input == "end") Right(state.copy(ended = true) -> Seq())
        else Left("some errors")
    }

  case object testMessage1 extends Message
  case object testMessage2 extends Message

  def messagePusher: TestStringPusher = new TestStringPusher()
}
