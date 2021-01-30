package io.github.scalaquest.core.dictionary.typeclass

import io.github.scalaquest.core.dictionary.{Ditransitive, Intransitive, Program, Transitive, Verb}
import io.github.scalaquest.core.model.Action.Common.{Inspect, Open, Take}

abstract class Generator[A, B: Monoid] {
  def generate(a: A): B
}

object Generator {
  // def apply[A: ProgramGenerator[A]#L]: Generator[A, Program] = implicitly[Generator[A, Program]]
  def apply[A: GeneratorLambda[A, B]#L, B]: Generator[A, B] = implicitly[Generator[A, B]]
}
