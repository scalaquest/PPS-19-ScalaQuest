/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.dictionary

import cats.Functor
import io.github.scalaquest.core.dictionary.implicits.{itemToProgram, programMonoid, verbToProgram}
import io.github.scalaquest.core.dictionary.generators.implicits.listGenerator
import io.github.scalaquest.core.dictionary.generators.{GeneratorK, combineAll}
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.parsing.scalog.Program

case class ProgramFromDictionary[I <: Item](verbs: Set[Verb], items: Set[I]) {

  def generateSource[F[_]: Functor](base: F[String]): F[String] = {
    val src = Functor[F].map(base)(
      _ +: combineAll(
        GeneratorK[List, Verb, Program].generate(verbs.toList),
        GeneratorK[List, Item, Program].generate(items.toList)
      ).map(_.generate).toList
    )
    Functor[F].map(src)(_.mkString("\n"))
  }
}
