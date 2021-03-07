package io.github.scalaquest.core.dictionary

import cats.implicits.catsStdInstancesForList
import cats.{Foldable, Monoid}

/**
 * This package provides two type classes `Generator` and `GeneratorK`. They are composable wrappers
 * around functions. In addition, there are facility methods that allow the creation of these type
 * classes.
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
 *     1 -> 1,
 *     2 -> 4,
 *     3 -> 9
 *   )
 * }}}
 */
package object generators extends GeneratorImplicits {

  /**
   * Allows to fold a `F[A]`.
   *
   * Inspired by:
   * [[https://typelevel.org/cats/typeclasses/monoid.html#example-usage-collapsing-a-list]].
   */
  def combineAll[F[_]: Foldable, A: Monoid](as: F[A]): A = Foldable[F].fold(as)

  /**
   * Variadic argument function that wraps the non-variadic `combineAll` one.
   */
  def combineAll[A: Monoid](a: A, as: A*): A = combineAll((a +: as).toList)
}
