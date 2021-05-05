/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.dictionary.generators

import cats.{Foldable, Functor, Monoid}

/**
 * A `Generator` that operates on kinds. It is used to wrap functions from `F[A]` to `B`.
 *
 * This type class is useful to work with `A`s that live in a context such as `Option` or `List`.
 *
 * It is necessary that `B` is a monoid instance, because its `empty` and `combine` are used to fold
 * on the `F`.
 */
class GeneratorK[F[_]: Functor: Foldable, A, +B: Monoid](implicit G: Generator[A, B])
  extends Generator[F[A], B] {

  override def generate(a: F[A]): B = {
    val b = Functor[F].map(a)(Generator[A, B].generate)
    Foldable[F].fold(b)
  }
}

object GeneratorK {

  /** Access to implicit `GeneratorK[F, A, B]`. */
  def apply[F[_], A, B](implicit G: GeneratorK[F, A, B]): GeneratorK[F, A, B] = G
}
