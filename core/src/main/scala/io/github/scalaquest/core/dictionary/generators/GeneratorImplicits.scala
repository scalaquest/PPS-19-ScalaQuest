package io.github.scalaquest.core.dictionary.generators

import cats.implicits.{catsKernelStdMonoidForMap, catsStdInstancesForList}
import cats.kernel.{Monoid, Semigroup}

/**
 * Mix-in to inherit implicit generators for collections.
 */
trait GeneratorImplicits {

  object implicits {

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
