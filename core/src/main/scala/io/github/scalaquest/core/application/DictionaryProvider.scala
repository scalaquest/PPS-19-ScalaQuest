package io.github.scalaquest.core.application

import cats.Functor
import io.github.scalaquest.core.dictionary.generators.GeneratorK
import io.github.scalaquest.core.dictionary.verbs.{Verb, VerbPrep}
import io.github.scalaquest.core.model.{Action, ItemRef, Model}

import scala.io.Source

trait DictionaryProvider[M0 <: Model] extends ApplicationStructure[M0] {
  import io.github.scalaquest.core.dictionary.generators.implicits.listToMapGenerator
  import io.github.scalaquest.core.dictionary.implicits.{itemToEntryGenerator, verbToEntryGenerator}

  def verbs: Set[Verb]

  def items: Set[I]

  def baseTheory: String = programFromResource(baseTheoryPath)

  final def baseTheoryPath: String = "base.pl"

  final def refToItem: Map[ItemRef, I] = GeneratorK[List, I, Map[ItemRef, I]].generate(items.toList)

  final def verbToAction: Map[VerbPrep, Action] =
    GeneratorK[List, Verb, Map[VerbPrep, Action]].generate(verbs.toList)

  private final def programSource[F[_]: Functor](base: F[String]): F[String] =
    ProgramFromDictionary(verbs, items).source(base)

  private final def programFromResource(resourceName: String): String = {
    type Id[X] = X
    programSource[Id](Source.fromResource(resourceName).mkString)
  }
}
