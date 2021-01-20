package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.{Action, Model}
import io.github.scalaquest.core.pipeline.parser.ParserResult

sealed trait Statement

object Statement {
  final case class Intransitive(action: Action)                          extends Statement
  final case class Transitive[II <: Model#I](action: Action, target: II) extends Statement

  final case class Ditransitive[II <: Model#I](action: Action, target1: II, target2: II)
    extends Statement
}

trait ResolverResult {
  def statement: Statement
}

case class SimpleResolverResult(statement: Statement) extends ResolverResult

trait Resolver {
  def resolve(parserResult: ParserResult): Either[String, ResolverResult]
}

trait ResolverFactory {
  def create(actions: Map[String, Action], items: Map[String, Model#Item]): Resolver
}
