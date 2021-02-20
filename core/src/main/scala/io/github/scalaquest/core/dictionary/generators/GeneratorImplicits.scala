package io.github.scalaquest.core.dictionary.generators

import cats.implicits.{catsKernelStdMonoidForMap, catsStdInstancesForList}
import cats.kernel.{Monoid, Semigroup}

/**
 * Mix-in to inherit implicit generators for collections.
 */
trait GeneratorImplicits {

  object implicits {

    /** Allows for the implicit creation of `Generator`s from `List[A]` to `B`. */
    implicit def listGenerator[A, B: Monoid](implicit G: Generator[A, B]): GeneratorK[List, A, B] =
      new GeneratorK[List, A, B]

    /**
     * Allows for the implicit creation of `Generator`s from `List[T]` to `Map[K, V].`
     *
     * @note:
     *   In the case there are many occurrences for the same key in the result, the second one is
     *   kept.
     */
    implicit def listToMapGenerator[T, K, V](implicit
      G: Generator[T, Map[K, V]]
    ): GeneratorK[List, T, Map[K, V]] = {
      implicit def keepRightSemigroup: Semigroup[V] = Semigroup.instance((_, b) => b)

      new GeneratorK[List, T, Map[K, V]]
    }

  }
}
