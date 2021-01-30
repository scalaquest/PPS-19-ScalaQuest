package io.github.scalaquest.core.dictionary.typeclass

trait Monoid[T] {
  def unit: T

  def combine(a: T, b: T): T
}

object Monoid {
  def apply[T: Monoid]: Monoid[T] = implicitly[Monoid[T]]
}
