package io.github.scalaquest.core

import io.github.scalaquest.core.model.{Message, Model}
import io.github.scalaquest.core.pipeline.Pipeline

trait Game[S <: Model#State] {
  def send(input: String)(state: S): Either[String, S]
}

trait GameTemplate[S <: Model#State] extends Game[S] {
  def pipelineFactory(state: S): Pipeline[S]

  override def send(input: String)(state: S): Either[String, S] =
    pipelineFactory(state) run input
}

object Game {
  def apply[S <: Model#State](_pipelineFactory: S => Pipeline[S]): Game[S] = new GameTemplate[S] {
    override def pipelineFactory(state: S): Pipeline[S] = _pipelineFactory(state)
  }
}

trait MessagePusher extends (Seq[Message] => Seq[String])
