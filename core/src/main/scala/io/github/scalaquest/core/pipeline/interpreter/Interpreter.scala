/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.resolver.{ResolverResult, Statement}

/**
 * A pipeline component that takes a [[Statement]] (wrapped into a [[ResolverResult]] ) and returns
 * a [[Model.Reaction]] wrapped into an [[InterpreterResult]]. The execution may fail, when the
 * given [[Statement]] does not generate a [[Model.Reaction]].
 * @tparam M
 *   The concrete type of the [[Model]] in use.
 * @tparam R
 *   The concrete type of the [[Model.Reaction]] in use. It should be derived from [[M]].
 */
trait Interpreter[M <: Model, R] {

  /**
   * Triggers the [[Interpreter]] execution.
   * @param resolverResult
   *   A wrapper for the input [[Statement]].
   * @return
   *   An [[Either]] describing the interpreter result. If the [[Interpreter]] fails, the result is
   *   a [[Left]] describing what went wrong. Otherwise, it is a [[Right]] with the
   *   [[InterpreterResult]] (wrapper for a [[Model.Reaction]] ).
   */
  def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult[R]]
}

/**
 * Companion object for the [[Interpreter]] trait. It exposes some utilities to instantiate the
 * [[Interpreter]] with the right constraints between types.
 *
 * It exposes an [[Interpreter::apply()]] to build an [[Interpreter]] <b>with the right types</b>,
 * as some constraints between the used types are defined. In addition, it provides a
 * [[Interpreter::builder()]] utility used into the pipeline to inject the [[Model.State]] in a
 * separate phase, compared to passing the state directly to [[Interpreter::interpret()]].
 */
object Interpreter {

  /**
   * A generator of [[Interpreter]] instances, giving the possibility to inject the [[Model.State]]
   * separately from the [[Interpreter::interpret()]] call.
   * @tparam M
   *   The concrete type of the [[Model]] in use.
   * @tparam S
   *   The concrete type of the [[Model.State]] in use. It should be derived from [[M]].
   * @tparam R
   *   The concrete type of the [[Model.Reaction]] in use. It should be derived from [[M]].
   */
  type Builder[M <: Model, S, R] = S => Interpreter[M, R]

  /**
   * It generates a [[Builder]] with the right type constraints.
   * @param model
   *   The concrete instance of the [[Model]] in use.
   * @tparam M
   *   The concrete type of the [[Model]] in use.
   * @return
   *   A [[Builder]] of [[Interpreter]] instances, with the right type constraints.
   */
  def builder[M <: Model](model: M): Builder[model.type, model.S, model.Reaction] =
    state => {
      implicit val s: model.S           = state
      val refToItem: RefToItem[model.I] = RefToItem(model)(state.items)
      val actionNotFound: String        = "I can't do that!"

      // shortcut for implementing the Interpreter, as it is a single method trait
      (resolverResult: ResolverResult) =>
        for {
          maybeReaction <- resolverResult.statement match {
            case Statement.Intransitive(action) =>
              state.ground.use(action).toRight(actionNotFound)

            case Statement.Transitive(action, refToItem(item)) =>
              item.use(action).toRight(actionNotFound)

            case Statement.Ditransitive(action, refToItem(directObj), refToItem(indirectObj)) =>
              directObj.use(action, Some(indirectObj)).toRight(actionNotFound)

            case _ => Left(actionNotFound)
          }
        } yield InterpreterResult(model)(maybeReaction)
    }
}
