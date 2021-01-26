package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli._
import io.github.scalaquest.core.model.{Message, RoomRef}
import io.github.scalaquest.core.{Game, MessagePusher}
import io.github.scalaquest.examples.escaperoom.House.kitchen
import io.github.scalaquest.examples.escaperoom.MyPipeline.pipelineBuilder

object Config {
  import myModel._
  val player: myModel.SimplePlayer = SimplePlayer(Set(), House.kitchen.ref)

  case object EatenMessage extends Message

  case class TextualMessage(msg: String) extends Message
  //val rooms = Map[RoomRef, Room](kitchen.ref -> kitchen)
  val rooms: Map[RoomRef, myModel.RM] = Map[RoomRef, myModel.RM](kitchen.ref -> kitchen)

  val state: SimpleState = SimpleState(
    actions,
    myModel.SimpleMatchState(
      player,
      ended = false,
      rooms,
      items
    ),
    Seq.empty[Message]
  )

  def parseMessage(msg: Message): String =
    msg match {
      case EatenMessage => "The apple was eaten!!"
    }

  val game: Game[Model]            = Game.fromModel(myModel).withPipelineBuilder(pipelineBuilder)
  val messagePusher: MessagePusher = MessagePusher(parseMessage)
  val cli: CLI                     = CLI.fromModel(myModel).build(state, game, messagePusher)
}

object EscapeRoom extends CLIApp {
  override def cli: CLI = Config.cli
}
