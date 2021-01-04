package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.{Action, Model}
import io.github.scalaquest.core.pipeline.parser.{AST, ParserResult}

sealed trait Statement

object Statement {
  final case class Intransitive(action: Action)                                          extends Statement
  final case class Transitive[II <: Model#I](action: Action, target: II)                 extends Statement
  final case class Ditransitive[II <: Model#I](action: Action, target1: II, target2: II) extends Statement
}

trait ResolverResult {
  def statement: Statement
}

case class SimpleResolverResult(statement: Statement) extends ResolverResult

object ResolverResult {
  def apply(statement: Statement): ResolverResult = SimpleResolverResult(statement)
}

trait Resolver {
  def resolve(parserResult: ParserResult): Either[String, ResolverResult]
}

case class SimpleResolver(actions: Set[Action], items: Set[Model#I]) extends Resolver {

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

  def retrieveItem(name: String): Either[String, Model#I] =
    items collectFirst { case item if item.name == name => item } toRight s"Couldn't understand $name"

  def retrieveAction(verb: String): Either[String, Action] =
    actions collectFirst { case action if action.name == verb => action } toRight s"Couldn't understand $verb."
}

object Resolver {
  def apply(actions: Set[Action], items: Set[Model#I]): Resolver = SimpleResolver(actions, items)
}
