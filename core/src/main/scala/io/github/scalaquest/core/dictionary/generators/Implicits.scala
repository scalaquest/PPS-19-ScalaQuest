package io.github.scalaquest.core.dictionary.generators

import cats.implicits.{
  catsKernelStdMonoidForMap,
  catsKernelStdSemilatticeForSet,
  catsStdInstancesForList
}
import cats.kernel.{Monoid, Semigroup}
import io.github.scalaquest.core.dictionary.{Program, Verb, VerbC, VerbPrep}
import io.github.scalaquest.core.model.{Action, ItemRef, Model}
import io.github.scalaquest.core.parsing.scalog.Clause

abstract class Implicits {

  object implicits {

    type Item = Model#Item

    implicit def verbGenerator: Generator[VerbC, Program] = (v: VerbC) => Set(v.clause)

    implicit def listGenerator[A, B: Monoid](implicit G: Generator[A, B]): GeneratorK[List, A, B] =
      new GeneratorK[List, A, B]

    implicit def listToMapGenerator[T, K, V](implicit
      G: Generator[T, Map[K, V]]
    ): GeneratorK[List, T, Map[K, V]] = {
      implicit def keepSemigroup[A]: Semigroup[A] = Semigroup.instance((a, _) => a)

      new GeneratorK[List, T, Map[K, V]]
    }

  }
}
