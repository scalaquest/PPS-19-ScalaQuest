/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.{Action, ItemRef}

sealed trait Statement

object Statement {
  final case class Intransitive(action: Action)             extends Statement
  final case class Transitive(action: Action, obj: ItemRef) extends Statement

  final case class Ditransitive(action: Action, directObj: ItemRef, indirectObj: ItemRef)
    extends Statement
}

/**
 * A wrapper for the output of the [[Resolver]] execution. It should contain a [[Statement]]
 * instance.
 */
trait ResolverResult {
  def statement: Statement
}

/**
 * A companion object for the [[ResolverResult]] trait. It exposes an [[ResolverResult::apply()]]
 * with a standard implementation of [[ResolverResult]].
 */
object ResolverResult {

  def apply(statement: Statement): ResolverResult = {
    case class SimpleResolverResult(statement: Statement) extends ResolverResult
    SimpleResolverResult(statement)
  }
}
