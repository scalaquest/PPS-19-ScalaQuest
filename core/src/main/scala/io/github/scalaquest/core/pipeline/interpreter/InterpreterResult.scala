package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model

trait InterpreterResult {
  def update: Model#Update
}

case class SimpleInterpreterResult(update: Model#Update) extends InterpreterResult

object InterpreterResult {
  def apply(update: Model#Update): InterpreterResult = SimpleInterpreterResult(update: Model#Update)
}
