package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli._
import io.github.scalaquest.core.model.StringPusher
import io.github.scalaquest.core.Game
import io.github.scalaquest.core.pipeline.Pipeline

abstract class GameCLIApp extends CLIApp {

  def pipelineBuilder: Pipeline.PartialBuilder[S, M]
  def state: S
  def messagePusher: StringPusher

  def source: String = programFromResource("base.pl")

  def game: Game[M] = Game builderFrom model build pipelineBuilder

  override def cli: CLI = CLI.builderFrom(model).build(state, game, messagePusher)

}

object EscapeRoom extends GameCLIApp {

  override def pipelineBuilder: Pipeline.PartialBuilder[S, M] = defaultPipeline(source)

  override def state: S =
    model.State(
      actions = verbToAction,
      rooms = House.refToRoom,
      items = refToItem,
      location = House.basement.ref,
      messages = Seq.empty
    )

  override def messagePusher: StringPusher = Pusher.defaultPusher
}
