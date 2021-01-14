package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model

trait InterpreterResult[R] {
  def reaction: R
}

object InterpreterResult {

  def apply[M <: Model](model: M)(reaction: model.Reaction): InterpreterResult[model.Reaction] = {
    case class SimpleInterpreterResult(reaction: model.Reaction) extends InterpreterResult[model.Reaction]
    SimpleInterpreterResult(reaction)
  }
}
