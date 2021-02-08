package io.github.scalaquest.core.application

import cats.Functor
import cats.implicits.catsKernelStdSemilatticeForSet
import io.github.scalaquest.core.dictionary.generators.{GeneratorK, combineAll}
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.dictionary.{Dictionary, Item}
import io.github.scalaquest.core.parsing.scalog.Program

case class ProgramFromDictionary[I <: Item](verbs: Set[Verb], items: Set[I]) {

  def source[F[_]: Functor](base: F[String]): F[String] = {
    import io.github.scalaquest.core.dictionary.implicits.{itemListToProgram, verbListToProgram}
    val source = Functor[F].map(base)(
      _ +: combineAll(
        GeneratorK[List, Verb, Program].generate(verbs.toList),
        GeneratorK[List, Item, Program].generate(items.toList)
      ).map(_.generate).toList
    )
    Functor[F].map(source)(_.mkString("\n"))
  }
}
