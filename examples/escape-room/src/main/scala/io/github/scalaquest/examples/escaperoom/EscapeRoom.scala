package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli._
import io.github.scalaquest.core.model.{Message, RoomRef, StringPusher}
import io.github.scalaquest.core.Game
import io.github.scalaquest.examples.escaperoom.MyPipeline.pipelineBuilder
import io.github.scalaquest.examples.escaperoom.Messages._

object Config {
  import model.{SimplePlayer, SimpleState}

  def player: SimplePlayer = SimplePlayer(Set(), House.kitchen.ref)

  def rooms: Map[RoomRef, Room] = House.refToRoom

  def state: SimpleState =
    SimpleState(
      verbToAction,
      model.SimpleMatchState(
        player,
        rooms,
        refToItem
      ),
      Seq.empty
    )

  def game: Game[Model]           = Game.fromModel(model).withPipelineBuilder(pipelineBuilder)
  def messagePusher: StringPusher = defaultPusher
  def cli: CLI                    = CLI.fromModel(model).build(state, game, messagePusher)
}

object EscapeRoom extends CLIApp {

  override def cli: CLI = Config.cli
}
