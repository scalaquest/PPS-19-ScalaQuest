package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli._
import io.github.scalaquest.core.model.{StringPusher}
import io.github.scalaquest.core.Game
import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.Pipeline.PipelineBuilder

abstract class GameCLIApp extends CLIApp {

  def pipelineBuilder: Pipeline.PartialBuilder[State, Model]
  def state: State
  def messagePusher: StringPusher

  def source: String = programFromResource("base.pl")

  def game: Game[Model] = Game builderFrom model build pipelineBuilder

  override def cli: CLI = CLI.builderFrom(model).build(state, game, messagePusher)

}

object EscapeRoom extends GameCLIApp {
  import model.{SimplePlayer, SimpleState}

  override def pipelineBuilder: Pipeline.PartialBuilder[State, Model] =
    defaultPipeline(source, model.SimpleGround)

  override def state: State =
    SimpleState(
      verbToAction,
      model.SimpleMatchState(
        SimplePlayer(Set(), House.kitchen.ref),
        House.refToRoom,
        refToItem
      ),
      Seq.empty
    )

  override def messagePusher: StringPusher = Messages.defaultPusher
}
