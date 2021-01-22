package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.{Action, ItemRef}
import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.parser.{AbstractSyntaxTree, ParserResult}

/**
 * A pipeline component that takes a [[AbstractSyntaxTree]] (wrapped into an [[ParserResult]] ) and
 * returns a [[Statement]] wrapped into an [[ResolverResult]]. The execution may fail, when the
 * given [[AbstractSyntaxTree]] has not a match with the [[Model]] components.
 */
trait Resolver {

  /**
   * Triggers the [[Resolver]] execution.
   * @param parserResult
   *   A wrapper for the input [[AbstractSyntaxTree]].
   * @return
   *   An [[Either]] describing the resolver result. If the [[Resolver]] fails, the result is a
   *   [[Left]] describing what went wrong. Otherwise, it is a [[Right]] with the [[ResolverResult]]
   *   (wrapper for a [[Statement]] ).
   */
  def resolve(parserResult: ParserResult): Either[String, ResolverResult]
}

/**
 * Companion object for the [[Resolver]] trait. It exposes the [[Resolver::apply()]] to instantiate
 * the [[Resolver]] .
 */
object Resolver {

  /**
   * It generates a [[Resolver]] with a standard implementation.
   *
   * @param actions
   *   A [[Map]] that links the textual representation of the [[Action]] to the instance.
   * @param items
   *   A [[Map]] that links the textual representation of the [[ItemRef]] to the instance.
   * @return
   *   A standard implementation of the [[Resolver]].
   */
  def apply(actions: Map[String, Action], items: Map[String, ItemRef]): Resolver = {

    /**
     * Higher order function that extracts the [[ItemRef]] from its textual representation. It may
     * fail, if the given representation does not match with an [[ItemRef]].
     */
    val retrieveItem: String => Either[String, ItemRef] =
      name => items get name toRight s"Couldn't understand $name"

    /**
     * Higher order function that extracts the [[Action]] from its textual representation. It may
     * fail, if the given representation does not match with an [[Action]].
     */
    val retrieveAction: String => Either[String, Action] =
      verb => actions get verb toRight s"Couldn't understand $verb."

    // shortcut for implementing the Resolver, as it is a single method trait.
    parserResult => {
      for {
        statement <- parserResult.tree match {
          case AbstractSyntaxTree.Intransitive(verb, _) =>
            for {
              action <- retrieveAction(verb)
            } yield Statement.Intransitive(action)

          case AbstractSyntaxTree.Transitive(verb, _, obj) =>
            for {
              action  <- retrieveAction(verb)
              itemRef <- retrieveItem(obj)
            } yield Statement.Transitive(action, itemRef)

          case AbstractSyntaxTree.Ditransitive(verb, _, directObj, indirectObj) =>
            for {
              action          <- retrieveAction(verb)
              directItemRef   <- retrieveItem(directObj)
              indirectItemRef <- retrieveItem(indirectObj)
            } yield Statement.Ditransitive(action, directItemRef, indirectItemRef)

          case _ => Left("The statement is wrong.")
        }
      } yield ResolverResult(statement)
    }
  }
}
