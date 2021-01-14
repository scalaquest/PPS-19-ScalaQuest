package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.model.Model

trait ReducerResult[S] {
  def state: S
}

object ReducerResult {

  trait ReducerResultBuilder[S] {
    def build(a: S): ReducerResult[S]
  }

  def apply[M <: Model](implicit model: M): ReducerResultBuilder[model.S] = {
    case class SimpleReducerResult(state: model.S) extends ReducerResult[model.S]
    SimpleReducerResult(_)
  }
}
