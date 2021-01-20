package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.{Action, ItemRef}
import io.github.scalaquest.core.pipeline.parser.{AbstractSyntaxTree, ParserResult}

trait Resolver {
  def resolve(parserResult: ParserResult): Either[String, ResolverResult]
}

object Resolver {

  def apply(actions: Map[String, Action], items: Map[String, ItemRef]): Resolver = {

    case class SimpleResolver(actions: Map[String, Action], items: Map[String, ItemRef])
      extends Resolver {

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

      def retrieveItem(name: String): Either[String, ItemRef] = {
        items get name toRight s"Couldn't understand $name"
      }

      def retrieveAction(verb: String): Either[String, Action] =
        actions get verb toRight s"Couldn't understand $verb."
    }

    SimpleResolver(actions, items)
  }
}
