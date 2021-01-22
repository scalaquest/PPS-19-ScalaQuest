package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.interpreter.InterpreterResult

/**
 * A pipeline component that takes a [[Model.Reaction]] (wrapped into an [[InterpreterResult]] ) and
 * returns a [[Model.State]] wrapped into an [[ReducerResult]]. The execution cannot fail.
 * @tparam M
 *   The concrete type of the [[Model]] in use.
 * @tparam S
 *   The concrete type of the [[Model.State]] in use. It should be derived from [[M]].
 * @tparam R
 *   The concrete type of the [[Model.Reaction]] in use. It should be derived from [[M]].
 */
trait Reducer[M <: Model, S, R] {

  /**
   * Triggers the [[Reducer]] execution.
   * @param interpreterResult
   *   A wrapper for the input [[Model.Reaction]].
   * @return
   *   A [[ReducerResult]] (wrapper for the updated [[Model.State]] ).
   */
  def reduce(interpreterResult: InterpreterResult[R]): ReducerResult[S]
}

/**
 * Companion object for the [[Reducer]] trait. It exposes some utilities to instantiate the
 * [[Reducer]] with the right constraints between types.
 *
 * It exposes an [[Reducer::apply()]] to build an [[Reducer]] <b>with the right types</b>, as some
 * constraints between the used types are defined. In addition, it provides a [[Reducer::builder()]]
 * utility used into the pipeline to inject the [[Model.State]] in a separate phase, compared to
 * passing the state directly to [[Reducer::reduce()]].
 */
object Reducer {

  /**
   * A generator of [[Reducer]] instances, giving the possibility to inject the [[Model.State]]
   * separately from the [[Reducer::reduce()]] call.
   * @tparam M
   *   The concrete type of the [[Model]] in use.
   * @tparam S
   *   The concrete type of the [[Model.State]] in use. It should be derived from [[M]].
   * @tparam R
   *   The concrete type of the [[Model.Reaction]] in use. It should be derived from [[M]].
   */
  type Builder[M <: Model, S, R] = S => Reducer[M, S, R]

  /**
   * It generates a [[Builder]] with the right type constraints.
   * @param model
   *   The concrete instance of the [[Model]] in use.
   * @tparam M
   *   The concrete type of the [[Model]] in use.
   * @return
   *   A [[Builder]] of [[Reducer]] instances, with the right type constraints.
   */
  def builder[M <: Model](implicit model: M): Builder[model.type, model.S, model.Reaction] =
    apply(model)(_)

  /**
   * It generates a [[Reducer]] with the right type constraints, and a standard implementation based
   * on them.
   * @param model
   *   The concrete instance of the [[Model]] in use.
   * @param state
   *   The concrete instance of the [[Model.State]] in use. The [[Model.State]] type must be derived
   *   from the `model` passed as parameter.
   * @tparam M
   *   The concrete type of the [[Model]] in use.
   * @return
   *   A [[Reducer]], with the right type constraints.
   */
  def apply[M <: Model](model: M)(state: model.S): Reducer[model.type, model.S, model.Reaction] = {

    case object SimpleReducer extends Reducer[model.type, model.S, model.Reaction] {
      override def reduce(
        interpreterResult: InterpreterResult[model.Reaction]
      ): ReducerResult[model.S] = {
        ReducerResult(model)(interpreterResult.reaction(state))
      }
    }

    SimpleReducer
  }
}
