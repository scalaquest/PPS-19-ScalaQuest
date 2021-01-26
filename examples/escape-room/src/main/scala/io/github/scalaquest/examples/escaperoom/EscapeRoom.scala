package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli._
import io.github.scalaquest.core.model.{Direction, ItemDescription, ItemRef, Message}
import io.github.scalaquest.core.{Game, MessagePusher}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.Pipeline.PipelineBuilder
import io.github.scalaquest.examples.escaperoom.MyPipeline.pipelineBuilder
import io.github.scalaquest.examples.escaperoom.House.{kitchen, livingRoom}

object Config {
  import myModel._
  val player = SimplePlayer(Set(), House.kitchen.ref)

  case object EatenMessage extends Message

  case class TextualMessage(msg: String) extends Message

  def kitchen: Room =
    Room(
      "kitchen",
      Map(
        Direction.East -> livingRoom.ref
      ),
      Set()
    )

  val state: myModel.SimpleState = SimpleState(
    actions,
    SimpleMatchState(
      player,
      ended = false,
      Map(kitchen.ref -> kitchen),
      Map()
    ),
    Seq()
  )

  def parseMessage(msg: Message): String =
    msg match {
      case EatenMessage => "The apple was eaten!!"
    }

  val game: Game[Model]            = Game.fromModel(myModel).withPipelineBuilder(pipelineBuilder)
  val messagePusher: MessagePusher = _.map(parseMessage)
  val cli: CLI                     = CLI.fromModel(myModel).build(state, game, messagePusher)
}

object EscapeRoom extends CLIApp {
  override def cli: CLI = Config.cli
}
