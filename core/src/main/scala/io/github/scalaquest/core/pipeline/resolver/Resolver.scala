package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.{Action, ItemRef}
import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.parser.{AbstractSyntaxTree, ParserResult}
import io.github.scalaquest.core.model.{Action, ItemRef, Model}
import io.github.scalaquest.core.pipeline.parser.{
  AbstractSyntaxTree,
  BaseItem,
  DecoratedItem,
  ItemDescription,
  ParserResult
}

import scala.annotation.tailrec

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
  type Builder[S] = S => Resolver

  abstract class SimpleResolver extends Resolver {

    def actions: PartialFunction[String, Action]

    def items: PartialFunction[ItemDescription, ItemRef]

    def retrieveAction(verb: String): Either[String, Action] =
      actions lift verb toRight s"Couldn't understand $verb."

    def retrieveItem(name: ItemDescription): Either[String, ItemRef] =
      items lift name toRight s"Couldn't understand $name"

    override def resolve(parserResult: ParserResult): Either[String, ResolverResult] = {
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

  def fromModel[M <: Model](implicit model: M): Builder[model.S] =
    s =>
      new SimpleResolver {

        override def actions: PartialFunction[String, Action] = s.actions

        override def items: PartialFunction[ItemDescription, ItemRef] =
          d =>
            s.game.itemsInScope.filter(i => d.isSubset(i.description)).toList match {
              case x :: Nil => x.itemRef
            }
      }

}
