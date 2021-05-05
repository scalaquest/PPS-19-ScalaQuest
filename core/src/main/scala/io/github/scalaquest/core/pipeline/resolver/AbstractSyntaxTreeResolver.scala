/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.dictionary.verbs.VerbPrep
import io.github.scalaquest.core.model.{Action, ItemDescription, ItemRef}
import io.github.scalaquest.core.pipeline.parser.{AbstractSyntaxTree, ParserResult}

abstract class AbstractSyntaxTreeResolver extends Resolver {

  def actions(v: VerbPrep): Either[String, Action]

  def items(d: ItemDescription): Either[String, ItemRef]

  override def resolve(parserResult: ParserResult): Either[String, ResolverResult] = {
    for {
      statement <- parserResult.tree match {
        case AbstractSyntaxTree.Intransitive(verb, prep, _) =>
          for {
            action <- actions((verb, prep))
          } yield Statement.Intransitive(action)

        case AbstractSyntaxTree.Transitive(verb, prep, _, obj) =>
          for {
            action  <- actions((verb, prep))
            itemRef <- items(obj)
          } yield Statement.Transitive(action, itemRef)

        case AbstractSyntaxTree.Ditransitive(verb, prep, _, directObj, indirectObj) =>
          for {
            action          <- actions((verb, prep))
            directItemRef   <- items(directObj)
            indirectItemRef <- items(indirectObj)
          } yield Statement.Ditransitive(action, directItemRef, indirectItemRef)

        case _ => Left("The statement is wrong.")
      }
    } yield ResolverResult(statement)
  }

}
