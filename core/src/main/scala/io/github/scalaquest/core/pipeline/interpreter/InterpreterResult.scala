package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model

trait InterpreterResult[R] {
  def reaction: R
}

object InterpreterResult {

  trait InterpreterResultBuilder[R] {
    def build(a: R): InterpreterResult[R]
  }

  def builder[M <: Model](implicit model: M): InterpreterResultBuilder[model.Reaction] = {
    case class SimpleInterpreterResult(reaction: model.Reaction) extends InterpreterResult[model.Reaction]
    SimpleInterpreterResult(_)
  }

  def apply[R](reaction: R): InterpreterResult[R] = {
    case class SimpleInterpreterResult(reaction: R) extends InterpreterResult[R]
    SimpleInterpreterResult(reaction)
  }
}
