package io.github.scalaquest.core.dictionary

trait Generator { self =>
  def generate: String

  def compose(other: Generator): Generator =
    new Generator {
      override def generate: String = self.generate + "\n" + other.generate
    }
}
