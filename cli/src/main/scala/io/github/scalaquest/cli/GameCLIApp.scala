/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.cli

import io.github.scalaquest.core.Game
import io.github.scalaquest.core.application.{
  DefaultPipelineProvider,
  DictionaryProvider,
  PipelineProvider
}
import io.github.scalaquest.core.model.{Message, MessagePusher, Model}
import io.github.scalaquest.core.pipeline.Pipeline

/** An application that uses a `Pipeline` instance to build a `CLI`. */
abstract class GameCLIApp[M0 <: Model](val model: M0)
  extends CLIApp
  with PipelineProvider[M0]
  with DefaultPipelineProvider[M0]
  with DictionaryProvider[M0] {

  def pipelineBuilder: Pipeline.PartialBuilder[S, M] = makePipeline

  def state: S

  def messagePusher: MessagePusher[String]

  def initialMessages: Seq[Message] = Seq.empty

  def game: Game[M] = Game.builderFrom[M](model).build(pipelineBuilder)

  override def cli: CLI =
    CLI.builderFrom[M](model).build(state, game, messagePusher, initialMessages)

}
