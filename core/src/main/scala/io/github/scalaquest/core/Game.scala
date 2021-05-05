/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core

import io.github.scalaquest.core.model.{Message, Model}
import io.github.scalaquest.core.pipeline.Pipeline.PartialBuilder

abstract class Game[M <: Model](val model: M) {

  def send(input: String)(state: model.S): Either[String, (model.S, Seq[Message])]
}

object Game {

  class GameBuilder[M <: Model](val model: M) {

    abstract class GameWithPipeline extends Game[model.type](model) {

      def partialBuilder: PartialBuilder[model.S, model.type]

      override def send(input: String)(state: model.S): Either[String, (model.S, Seq[Message])] =
        partialBuilder(state) run input
    }

    def build(
      _partialBuilder: PartialBuilder[model.S, model.type]
    ): Game[model.type] =
      new GameWithPipeline {
        override def partialBuilder: PartialBuilder[model.S, model.type] = _partialBuilder
      }
  }

  def builderFrom[M <: Model](model: M): GameBuilder[M] = new GameBuilder(model)
}
