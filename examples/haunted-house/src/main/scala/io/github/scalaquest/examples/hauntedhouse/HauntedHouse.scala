package io.github.scalaquest.examples.hauntedhouse

import io.github.scalaquest.cli._
import io.github.scalaquest.core.model.StringPusher
import io.github.scalaquest.core.Game
import io.github.scalaquest.core.pipeline.Pipeline

abstract class GameCLIApp extends CLIApp {

  def pipelineBuilder: Pipeline.PartialBuilder[State, Model]
  def state: State
  def messagePusher: StringPusher

  def source: String = programFromResource("base.pl")

  def game: Game[Model] = Game builderFrom model build pipelineBuilder

  override def cli: CLI = CLI.builderFrom(model).build(state, game, messagePusher)

}

object EscapeRoom extends GameCLIApp {

  override def pipelineBuilder: Pipeline.PartialBuilder[State, Model] = defaultPipeline(source)

  override def state: State =
    model.stateBuilder(
      actions = verbToAction,
      rooms = House.refToRoom,
      items = refToItem,
      location = House.kitchen.ref,
      messages = Seq.empty
    )

  override def messagePusher: StringPusher = Messages.defaultPusher
}
