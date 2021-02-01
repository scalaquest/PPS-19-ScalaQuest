package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.dictionary.VerbPrep
import io.github.scalaquest.core.model.{Action, ItemDescription, ItemRef, Model}
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

  type Builder[S] = S => Resolver

  abstract class SimpleResolver extends Resolver {

    def actions: PartialFunction[VerbPrep, Action]

    def items: PartialFunction[ItemDescription, ItemRef]

    def retrieveAction(verbPrep: VerbPrep): Either[String, Action] =
      actions lift verbPrep toRight s"Couldn't understand ${verbPrep._1}."

    def retrieveItem(name: ItemDescription): Either[String, ItemRef] =
      items lift name toRight s"Couldn't understand $name"

    override def resolve(parserResult: ParserResult): Either[String, ResolverResult] = {
      for {
        statement <- parserResult.tree match {
          case AbstractSyntaxTree.Intransitive(verb, prep, _) =>
            for {
              action <- retrieveAction((verb, prep))
            } yield Statement.Intransitive(action)

          case AbstractSyntaxTree.Transitive(verb, prep, _, obj) =>
            for {
              action  <- retrieveAction((verb, prep))
              itemRef <- retrieveItem(obj)
            } yield Statement.Transitive(action, itemRef)

          case AbstractSyntaxTree.Ditransitive(verb, prep, _, directObj, indirectObj) =>
            for {
              action          <- retrieveAction((verb, prep))
              directItemRef   <- retrieveItem(directObj)
              indirectItemRef <- retrieveItem(indirectObj)
            } yield Statement.Ditransitive(action, directItemRef, indirectItemRef)

          case _ => Left("The statement is wrong.")
        }
      } yield ResolverResult(statement)
    }

  }

  def builder[M <: Model](implicit model: M): Builder[model.S] =
    s =>
      new SimpleResolver {

        override def actions: PartialFunction[VerbPrep, Action] = s.actions

        override def items: PartialFunction[ItemDescription, ItemRef] =
          d =>
            s.matchState.itemsInScope.filter(i => d.isSubset(i.description)).toList match {
              case x :: Nil => x.ref
            }
      }
}
