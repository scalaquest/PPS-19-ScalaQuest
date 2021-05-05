/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.{Action, ItemDescription, ItemRef, Model}
import io.github.scalaquest.core.pipeline.parser.ParserResult

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

  def builder[M <: Model](implicit model: M): Builder[model.S] =
    s =>
      new AbstractSyntaxTreeResolver {

        override def actions(v: (String, Option[String])): Either[String, Action] =
          s.actions get v toRight s"I couldn't understand what you said."

        override def items(d: ItemDescription): Either[String, ItemRef] =
          s.scope.filter(i => d.isSubset(i.description)).toList match {
            case x :: Nil => Right(x.ref)
            case _ :: _   => Left(s"Which ${d.mkString} are you talking about?")
            case _        => Left(s"I can't see any ${d.mkString}.")
          }
      }
}
