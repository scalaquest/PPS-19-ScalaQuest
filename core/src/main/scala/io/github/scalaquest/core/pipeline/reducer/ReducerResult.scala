/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.model.{Message, Model}

/**
 * A wrapper for the output of the [[Reducer]] execution. It should contain a [[Model.State]]
 * instance.
 * @tparam S
 *   The [[Model.State]] concrete type.
 */
trait ReducerResult[S] {
  def state: S
  def message: Seq[Message]
}

/**
 * A companion object for the [[ReducerResult]] trait. It exposes an [[ReducerResult::apply()]] to
 * instantiate the [[ReducerResult]] with the right constraints between types.
 */
object ReducerResult {

  /**
   * Builds an [[ReducerResult]], with the right constraints between types.
   * @param model
   *   The concrete instance of the [[Model]] in use.
   * @param state
   *   The [[Model.State]] instance to be wrapped. The [[Model.State]] type must be derived from the
   *   `model` passed as parameter.
   * @tparam M
   *   The concrete type of the [[Model]] in use.
   * @return
   *   An [[ReducerResult]], with the right type constraints.
   */
  def apply[M <: Model](model: M)(state: model.S, message: Seq[Message]): ReducerResult[model.S] = {
    case class SimpleReducerResult(state: model.S, message: Seq[Message])
      extends ReducerResult[model.S]
    SimpleReducerResult(state, message)
  }
}
