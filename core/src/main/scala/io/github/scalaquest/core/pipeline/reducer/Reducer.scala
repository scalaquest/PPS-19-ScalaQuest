package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.interpreter.InterpreterResult

trait ReducerResult[S <: Model#State] {
  def state: S
}

trait Reducer[S <: Model#State] {
  def reduce(interpreterResult: InterpreterResult): ReducerResult[S]
}

object Reducer {
  def apply[S <: Model#State](state: S): Reducer[S] = ???
}
