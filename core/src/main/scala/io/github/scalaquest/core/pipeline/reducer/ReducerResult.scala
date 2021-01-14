package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.model.Model

trait ReducerResult[S] {
  def state: S
}

object ReducerResult {

  def apply[M <: Model](model: M)(state: model.S): ReducerResult[model.S] = {
    case class SimpleReducerResult(state: model.S) extends ReducerResult[model.S]
    SimpleReducerResult(state)
  }
}
