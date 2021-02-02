package io.github.scalaquest.core.dictionary

import cats.implicits.catsStdInstancesForList
import cats.{Foldable, Monoid}

package object generators extends Implicits {

  def combineAll[F[_]: Foldable, A: Monoid](as: F[A]): A = Foldable[F].fold(as)

  def combineAll[A: Monoid](a: A, as: A*): A = combineAll((a +: as).toList)
}
