package io.github.scalaquest.core.dictionary.generators

trait Generator[-A, +B] {
  def generate(a: A): B
}

object Generator {
  def apply[A, B](implicit G: Generator[A, B]): Generator[A, B] = G

  def instance[A, B](gen: A => B): Generator[A, B] = (a: A) => gen(a)

  def makeEntry[T, K, V](f: T => (K, V)): Generator[T, Map[K, V]] = instance(t => Map(f(t)))
}
