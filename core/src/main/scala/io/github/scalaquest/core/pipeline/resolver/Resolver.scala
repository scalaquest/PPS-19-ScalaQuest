package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.{Action, Model}
import io.github.scalaquest.core.pipeline.parser.{AST, ParserResult}

trait Resolver {
  def resolve(parserResult: ParserResult): Either[String, ResolverResult]
}

case class SimpleResolver(actions: Set[Action], items: Set[Model#Item]) extends Resolver {

  override def resolve(parserResult: ParserResult): Either[String, ResolverResult] = {
    val statement = parserResult.tree match {
      case AST.Intransitive(verb, _) =>
        for {
          action <- retrieveAction(verb)
        } yield Statement.Intransitive(action)

      case AST.Transitive(verb, _, complement) =>
        for {
          action   <- retrieveAction(verb)
          mainItem <- retrieveItem(complement)
        } yield Statement.Transitive(action, mainItem)

      case AST.Ditransitive(verb, _, mainComplement, sideComplement) =>
        for {
          action   <- retrieveAction(verb)
          mainItem <- retrieveItem(mainComplement)
          sideItem <- retrieveItem(sideComplement)
        } yield Statement.Ditransitive(action, mainItem, sideItem)

      case _ => Left("The statement is wrong.")
    }

    statement.map(ResolverResult(_))
  }

  def retrieveItem(name: String): Either[String, Model#Item] =
    items collectFirst { case item if item.name == name => item } toRight s"Couldn't understand $name"

  def retrieveAction[A](verb: String): Either[String, Action] =
    actions collectFirst { case action if action.name == verb => action } toRight s"Couldn't understand $verb."
}

object Resolver {
  def apply(actions: Set[Action], items: Set[Model#Item]): Resolver = SimpleResolver(actions, items)
}
