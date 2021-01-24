package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.{Action, ItemRef, Model}
import io.github.scalaquest.core.pipeline.parser.{
  AbstractSyntaxTree,
  BaseItem,
  DecoratedItem,
  ItemDescription,
  ParserResult
}

import scala.annotation.tailrec

trait Resolver {
  def resolve(parserResult: ParserResult): Either[String, ResolverResult]
}

/*
 * Ci sono due chiavi:
 * - chiave rossa
 * - chiave blu
 *
 * Chiedo "prendi la chiave":
 * - se il resolver non risolve:
 *     Interpreter cerca "chiave": ne trova 2
 * - se il resolver risolve:
 *     Resolver dice "chiave" si riferisce a due oggetti
 */

object Resolver {

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
