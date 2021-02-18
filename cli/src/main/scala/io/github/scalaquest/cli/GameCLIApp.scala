package io.github.scalaquest.cli

import io.github.scalaquest.core.Game
import io.github.scalaquest.core.application.{
  DefaultPipelineProvider,
  DictionaryProvider,
  PipelineProvider
}
import io.github.scalaquest.core.model.{Message, MessagePusher, Model}
import io.github.scalaquest.core.pipeline.Pipeline

abstract class GameCLIApp[M0 <: Model](val model: M0)
  extends CLIApp
  with PipelineProvider[M0]
  with DefaultPipelineProvider[M0]
  with DictionaryProvider[M0] {

  def pipelineBuilder: Pipeline.PartialBuilder[S, M] = makePipeline

  def state: S

  def messagePusher: MessagePusher[String]

  def initialMessages: Seq[Message]

  def game: Game[M] = Game.builderFrom[M](model).build(pipelineBuilder)

  override def cli: CLI =
    CLI.builderFrom[M](model).build(state, game, messagePusher, initialMessages)

}
