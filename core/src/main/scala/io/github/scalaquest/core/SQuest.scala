package io.github.scalaquest.core

import io.github.scalaquest.core.model.{Message, Model}
import io.github.scalaquest.core.pipeline.Pipeline

trait SQuest[S <: Model#State] {
  def send(input: String)(state: S): Either[String, S]
}

trait SQuestTemplate[S <: Model#State] extends SQuest[S] {

  def pipelineFactory(state: S): Pipeline[S]

  override def send(input: String)(state: S): Either[String, S] =
    pipelineFactory(state) run input
}

trait MessagePusher extends (Seq[Message] => Seq[String])

object SQuest {

  def apply[S <: Model#State](
      _pipelineFactory: S => Pipeline[S]
  ): SQuest[S] = new SQuestTemplate[S] {
    override def pipelineFactory(state: S): Pipeline[S] = _pipelineFactory(
      state
    )
  }
}
