package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.model.Model

trait Generator { self =>
  def generate: Program

  def compose(other: Generator): Generator =
    new Generator {
      override def generate: Program = self.generate + programSeparator + other.generate
    }
}

case class VerbGenerator(verbs: List[BaseVerb with ClauseUtils]) extends Generator {
  override def generate: Program = verbs.map(_.clause.generate).mkString(programSeparator)
}

case class ItemGenerator(items: List[Model#Item]) extends Generator {
  import io.github.scalaquest.core.parsing.scalog.dsl._

  val name      = CompoundBuilder("name").constructor
  val adjective = CompoundBuilder("adjective").constructor

  private def names: List[String] =
    items
      .map(_.description.base)
      .map(_.name)
      .map(name(_))
      .map(_.generate)

  private def adjectives: List[String] =
    items
      .flatMap(_.description.decorators)
      .map(adjective(_))
      .map(_.generate)

  override def generate: Program = (names ++ adjectives).mkString(programSeparator)
}
