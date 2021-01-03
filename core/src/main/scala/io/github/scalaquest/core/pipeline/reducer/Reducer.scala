package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.interpreter.InterpreterResult

trait ReducerResult[M <: Model] {
  def state: M#S
}

trait Reducer[M <: Model] {
  def reduce(interpreterResult: InterpreterResult[M]): ReducerResult[M]
}

object Reducer {
  def apply[M <: Model](state: M#S): Reducer[M] = ???
}
