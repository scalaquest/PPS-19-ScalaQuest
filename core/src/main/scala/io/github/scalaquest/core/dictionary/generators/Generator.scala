package io.github.scalaquest.core.dictionary.generators

import io.github.scalaquest.core.dictionary.{Program, Verb}
import io.github.scalaquest.core.parsing.scalog.Clause

abstract class Generator[-A, +B] {
  def generate(a: A): B
}

object Generator {
  def apply[A, B](implicit G: Generator[A, B]): Generator[A, B] = G
}
