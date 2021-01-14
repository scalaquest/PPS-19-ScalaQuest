package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.interpreter.InterpreterResult

trait Reducer[M <: Model, S, R] {
  def reduce(interpreterResult: InterpreterResult[R]): ReducerResult[S]
}

object Reducer {
  type ReducerBuilder[M <: Model, S, R] = S => Reducer[M, S, R]

  def apply[M <: Model](implicit model: M): ReducerBuilder[model.type, model.S, model.Reaction] = {

    case class SimpleReducer(state: model.S) extends Reducer[model.type, model.S, model.Reaction] {
      override def reduce(interpreterResult: InterpreterResult[model.Reaction]): ReducerResult[model.S] = {
        ReducerResult(model) build interpreterResult.reaction(state)
      }
    }

    SimpleReducer
  }
}
