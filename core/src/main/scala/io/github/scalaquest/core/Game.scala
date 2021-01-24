package io.github.scalaquest.core

import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.model.{Message, Model}
import io.github.scalaquest.core.pipeline.Pipeline.PipelineBuilder

abstract class Game[M <: Model](val model: M) {

  def send(input: String)(state: model.S): Either[String, model.S]
}

trait MessagePusher extends (Seq[Message] => Seq[String])

object Game {

  class GameFromModel[M <: Model](val model: M) {

    abstract class GameWithPipeline extends Game[model.type](model) {

      def pipelineFactory: PipelineBuilder[model.S, model.type]

      override def send(input: String)(state: model.S): Either[String, model.S] =
        pipelineFactory(state) run input
    }

    def withPipelineBuilder(
      pipelineBuilder: PipelineBuilder[model.S, model.type]
    ): Game[model.type] =
      new GameWithPipeline {
        override def pipelineFactory: PipelineBuilder[model.S, model.type] = pipelineBuilder
      }
  }

  def fromModel[M <: Model](implicit model: M): GameFromModel[M] = new GameFromModel(model)
}

object ExampleUsage {
  implicit val model: SimpleModel.type                      = SimpleModel
  val pipelineBuilder: PipelineBuilder[model.S, model.type] = ???

  val game: Game[model.type] = Game fromModel model withPipelineBuilder pipelineBuilder
}
