package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.pipeline.interpreter.ItemRef

sealed trait Statement

object Statement {
  final case class Intransitive(action: Action)             extends Statement
  final case class Transitive(action: Action, obj: ItemRef) extends Statement

  final case class Ditransitive(action: Action, directObj: ItemRef, indirectObj: ItemRef)
    extends Statement
}

trait ResolverResult {
  def statement: Statement
}

object ResolverResult {

  def apply(statement: Statement): ResolverResult = {
    case class SimpleResolverResult(statement: Statement) extends ResolverResult
    SimpleResolverResult(statement)
  }
}
