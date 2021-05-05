/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model

/**
 * A wrapper for the output of the [[Interpreter]] execution. It should contain a [[Model.Reaction]]
 * instance.
 * @tparam R
 *   The [[Model.Reaction]] concrete type.
 */
trait InterpreterResult[R] {
  def reaction: R
}

/**
 * A companion object for the [[InterpreterResult]] trait. It exposes an
 * [[InterpreterResult::apply()]] to instantiate the [[InterpreterResult]] with the right
 * constraints between types.
 */
object InterpreterResult {

  /**
   * Builds an [[InterpreterResult]], with the right constraints between types.
   * @param model
   *   The concrete instance of the [[Model]] in use.
   * @param reaction
   *   The [[Model.Reaction]] instance to be wrapped. The [[Model.Reaction]] type must be derived
   *   from the `model` passed as parameter.
   * @tparam M
   *   The concrete type of the [[Model]] in use.
   * @return
   *   An [[InterpreterResult]], with the right type constraints.
   */
  def apply[M <: Model](model: M)(reaction: model.Reaction): InterpreterResult[model.Reaction] = {
    case class SimpleInterpreterResult(reaction: model.Reaction)
      extends InterpreterResult[model.Reaction]
    SimpleInterpreterResult(reaction)
  }
}
