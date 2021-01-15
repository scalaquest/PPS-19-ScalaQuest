package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.interpreter.InterpreterResult

trait Reducer[M <: Model, S, R] {
  def reduce(interpreterResult: InterpreterResult[R]): ReducerResult[S]
}

object Reducer {
  type Builder[M <: Model, S, R] = S => Reducer[M, S, R]

  def builder[M <: Model](implicit model: M): Builder[model.type, model.S, model.Reaction] = apply(model)(_)

  def apply[M <: Model](model: M)(state: model.S): Reducer[model.type, model.S, model.Reaction] = {

    case class SimpleReducer(state: model.S) extends Reducer[model.type, model.S, model.Reaction] {
      override def reduce(interpreterResult: InterpreterResult[model.Reaction]): ReducerResult[model.S] = {
        ReducerResult(model)(interpreterResult.reaction(state))
      }
    }

    SimpleReducer(state)
  }
}
