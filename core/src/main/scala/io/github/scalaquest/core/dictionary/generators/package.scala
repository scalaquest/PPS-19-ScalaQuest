package io.github.scalaquest.core.dictionary

import cats.implicits.catsStdInstancesForList
import cats.{Foldable, Monoid}

/**
 * This package provides some useful utilities to create every data structure. Here it's possible to
 * create a generator of singlets or a single map entry, and, [[GeneratorK]] allows to create maps
 * with multiple entry. This package provides also a set of implicits methods.
 *
 * For example:
 * {{{
 *   implicit val generator = Generator.makeEntry((x: Int) => x -> Math.pow(x, 2))
 *   val generatorK = new GeneratorK[List, Int, Map[Int, Double]]
 * }}}
 *
 * A [[GeneratorK]] can create a Map from a List.
 * {{{
 *   generatorK.generate(List(1, 2, 3))
 * }}}
 *
 * Should result in the following being printed:
 * {{{
 *   Map(
 *   1 -> 1,
 *   2 -> 4,
 *   3 -> 9
 *   )
 * }}}
 */

package object generators extends GeneratorImplicits {

  def combineAll[F[_]: Foldable, A: Monoid](as: F[A]): A = Foldable[F].fold(as)

  def combineAll[A: Monoid](a: A, as: A*): A = combineAll((a +: as).toList)
}
