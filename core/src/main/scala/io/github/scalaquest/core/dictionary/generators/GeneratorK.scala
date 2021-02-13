package io.github.scalaquest.core.dictionary.generators

import cats.{Foldable, Functor, Monoid}

class GeneratorK[F[_]: Functor: Foldable, A, +B: Monoid](implicit G: Generator[A, B])
  extends Generator[F[A], B] {

  override def generate(a: F[A]): B = {
    val b = Functor[F].map(a)(Generator[A, B].generate)
    Foldable[F].fold(b)
  }
}

object GeneratorK {

  def apply[F[_], A, B](implicit G: GeneratorK[F, A, B]): GeneratorK[F, A, B] = G
}
