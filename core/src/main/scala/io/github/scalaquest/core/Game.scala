package io.github.scalaquest.core

import io.github.scalaquest.core.model.{Message, Model}
import io.github.scalaquest.core.pipeline.Pipeline

trait Game[M <: Model] {
  def send(input: String)(state: M#S): Either[String, M#S]
}

trait GameTemplate[M <: Model] extends Game[M] {
  def pipelineFactory(state: M#S): Pipeline[M]
  override def send(input: String)(state: M#S): Either[String, M#S] = pipelineFactory(state) run input
}

trait MessagePusher extends (Seq[Message] => Seq[String])

object Game {

  def apply[M <: Model](_pipelineFactory: M#S => Pipeline[M]): Game[M] =
    new GameTemplate[M] {
      override def pipelineFactory(state: M#S): Pipeline[M] = _pipelineFactory(state)
    }
}
