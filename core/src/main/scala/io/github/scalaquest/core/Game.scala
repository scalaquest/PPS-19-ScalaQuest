package io.github.scalaquest.core

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.Pipeline.PartialBuilder

abstract class Game[M <: Model](val model: M) {

  def send(input: String)(state: model.S): Either[String, model.S]
}

object Game {

  class GameBuilder[M <: Model](val model: M) {

    abstract class GameWithPipeline extends Game[model.type](model) {

      def partialBuilder: PartialBuilder[model.S, model.type]

      override def send(input: String)(state: model.S): Either[String, model.S] =
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
