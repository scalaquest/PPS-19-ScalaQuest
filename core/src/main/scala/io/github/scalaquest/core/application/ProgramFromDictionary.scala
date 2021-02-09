package io.github.scalaquest.core.application

import cats.{Functor, Monoid}
import io.github.scalaquest.core.dictionary.generators.{Generator, GeneratorK, combineAll}
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.dictionary.{Dictionary, Item}
import io.github.scalaquest.core.parsing.scalog.{Clause, Program}
import io.github.scalaquest.core.dictionary.implicits.{verbToProgram, itemToProgram, programMonoid}
import io.github.scalaquest.core.dictionary.generators.implicits.listGenerator

case class ProgramFromDictionary[I <: Item](dictionary: Dictionary[I]) {

  def source[F[_]: Functor](base: F[String]): F[String] = {
    val src = Functor[F].map(base)(
      _ +: combineAll(
        GeneratorK[List, Verb, Program].generate(dictionary.verbs),
        GeneratorK[List, Item, Program].generate(dictionary.items)
      ).map(_.generate).toList
    )
    Functor[F].map(src)(_.mkString("\n"))
  }
}
