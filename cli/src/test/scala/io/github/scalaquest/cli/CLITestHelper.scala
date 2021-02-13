package io.github.scalaquest.cli

import io.github.scalaquest.core.model.{Message, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel

object CLITestHelper {

  def model: SimpleModel.type = SimpleModel

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

      override def returns(input: String): Either[String, SimpleModel.SimpleState] =
        Right(
          if (input == "end") state.copy(ended = true, messages = Seq())
          else state.copy(_location = RoomRef("2"), messages = Seq(testMessage2))
        )
    }

  def gameLeft: TestGame[SimpleModel.type] =
    new TestGame(model) {

      override def returns(input: String): Either[String, SimpleModel.SimpleState] =
        if (input == "end") Right(state.copy(ended = true, messages = Seq()))
        else Left("some errors")
    }

  case object testMessage1 extends Message
  case object testMessage2 extends Message

  def messagePusher: TestStringPusher = new TestStringPusher()
}
