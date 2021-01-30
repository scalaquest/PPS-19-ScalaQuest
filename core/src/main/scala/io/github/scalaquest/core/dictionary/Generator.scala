package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.parsing.scalog.Clause

trait Generator { self =>
  def generate: Program

  def compose(other: Generator): Generator =
    new Generator {
      override def generate: Program = self.generate ++ other.generate
    }
}

case class VerbGenerator(verbs: List[BaseVerb with ClauseUtils]) extends Generator {
  override def generate: Program = verbs.map(_.clause).toSet
}

case class ItemGenerator(items: List[Model#Item]) extends Generator {
  import io.github.scalaquest.core.parsing.scalog.dsl.{CompoundBuilder, stringToAtom, termToFact}

  val name      = CompoundBuilder("name").constructor
  val adjective = CompoundBuilder("adjective").constructor

  private def names: List[Clause] =
    items
      .map(_.description.base)
      .map(_.name)
      .map(name(_))

  private def adjectives: List[Clause] =
    items
      .flatMap(_.description.decorators)
      .map(adjective(_))

  override def generate: Program = (names ++ adjectives).toSet
}
