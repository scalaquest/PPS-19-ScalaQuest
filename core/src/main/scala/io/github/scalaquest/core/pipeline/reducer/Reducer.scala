package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.interpreter.InterpreterResult

trait ReducerResult[S <: Model#State] {
  def state: S
}

trait Reducer[S <: Model#State] {
  def reduce[R](interpreterResult: InterpreterResult[R]): ReducerResult[S]
}

object Reducer {
  def apply[M <: Model](implicit model: M): Reducer[model.S] = ???
}
