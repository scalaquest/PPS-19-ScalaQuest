package io.github.scalaquest.cli

import io.github.scalaquest.core.Game
import io.github.scalaquest.core.model.{MessagePusher, Model}
import io.github.scalaquest.core.pipeline.Pipeline

abstract class GameCLIApp[M0 <: Model](val model: M0) extends CLIApp {

  type M = model.type
  type S = model.S

  def pipelineBuilder: Pipeline.PartialBuilder[S, M]

  def state: S

  def messagePusher: MessagePusher[String]

  def game: Game[M] = Game.builderFrom[M](model).build(pipelineBuilder)

  override def cli: CLI = CLI.builderFrom[M](model).build(state, game, messagePusher)

}
