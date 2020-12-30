package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.{Action, Model}

sealed trait Statement

object Statement {
  final case class Intransitive(action: Action)                                             extends Statement
  final case class Transitive(action: Action, mainItem: Model#Item)                         extends Statement
  final case class Ditransitive(action: Action, mainItem: Model#Item, sideItem: Model#Item) extends Statement
}

trait ResolverResult {
  def statement: Statement
}

case class SimpleResolverResult(statement: Statement) extends ResolverResult

object ResolverResult {
  def apply(statement: Statement): ResolverResult = SimpleResolverResult(statement)
}
