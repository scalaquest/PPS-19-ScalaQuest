package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.resolver.ResolverResult

trait InterpreterResult {
  def effect: Model#Effect
}

trait Interpreter[S <: Model#State] {
  def interpret(
      resolverResult: ResolverResult
  ): Either[String, InterpreterResult]
}

object Interpreter {
  def apply[S <: Model#State](state: S): Interpreter[S] = ???
}
