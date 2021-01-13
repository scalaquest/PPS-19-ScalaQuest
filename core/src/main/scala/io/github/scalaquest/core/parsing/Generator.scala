package io.github.scalaquest.core.parsing

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.parsing.engine.ListP

trait Generator { self =>
  def generate: String

  def compose(other: Generator): Generator =
    new Generator {
      override def generate: String = self.generate + "\n" + other.generate
    }
}

case class GrammarGenerator(verbs: Verb*) extends Generator {
  override def generate: String = verbs.map(_.clause.generate).mkString("\n")
}

case class ItemGenerator(items: Model#Item*) extends Generator {

  import io.github.scalaquest.core.parsing.engine.clause.dsl._

  def np: CompoundBuilder = CompoundBuilder("np")

  override def generate: String =
    items
      .map(i => np(i.name) --> ListP(i.name))
      .map(_.generate)
      .mkString("\n")
}
