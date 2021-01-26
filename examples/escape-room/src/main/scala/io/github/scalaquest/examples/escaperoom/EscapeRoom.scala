package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli._
import io.github.scalaquest.core.model.{Message, RoomRef}
import io.github.scalaquest.core.{Game, MessagePusher}
import io.github.scalaquest.examples.escaperoom.MyPipeline.pipelineBuilder

object Config {
  import myModel.{SimplePlayer, SimpleState}

  def player: myModel.SimplePlayer = SimplePlayer(Set(), House.kitchen.ref)

  case object Eaten             extends Message
  case class Print(msg: String) extends Message

  case class TextualMessage(msg: String) extends Message
  def rooms: Map[RoomRef, Room] = House.genMap
  println(rooms)

  def state: SimpleState =
    SimpleState(
      actions,
      myModel.SimpleMatchState(
        player,
        rooms,
        items
      ),
      Seq.empty[Message]
    )

  def parseMessage: PartialFunction[Message, String] = { case Eaten =>
    "The apple was eaten!!"
  }

  def game: Game[Model]            = Game.fromModel(myModel).withPipelineBuilder(pipelineBuilder)
  def messagePusher: MessagePusher = MessagePusher(parseMessage)
  def cli: CLI                     = CLI.fromModel(myModel).build(state, game, messagePusher)
}

object EscapeRoom extends CLIApp {
  override def cli: CLI = Config.cli
}
