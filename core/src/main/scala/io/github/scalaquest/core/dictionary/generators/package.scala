package io.github.scalaquest.core.dictionary

import cats.implicits.{catsStdInstancesForList}
import cats.{Foldable, Monoid}
import io.github.scalaquest.core.model.Model

package object generators extends Implicits {

  type Item = Model#Item

  def combineAll[F[_]: Foldable, A: Monoid](as: F[A]): A = Foldable[F].fold(as)

  def combineAll[A: Monoid](a: A, as: A*): A = combineAll((a +: as).toList)
}
