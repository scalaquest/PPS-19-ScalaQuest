package io.github.scalaquest.core.dictionary.generators

/**
 * A type class modeling a relation between two types. It is used as a composable wrapper around a
 * function from A to B.
 */
trait Generator[-A, +B] {

  /**
   * Generates the output value for a given element.
   * @param a
   *   the input element
   * @return
   *   the output value
   */
  def generate(a: A): B
}

object Generator {

  /** Access to implicit `Generator[A, B]`. */
  def apply[A, B](implicit G: Generator[A, B]): Generator[A, B] = G

  /** Creates a generator wrapping a function from `A` to `B`. */
  def instance[A, B](gen: A => B): Generator[A, B] = (a: A) => gen(a)

  /**
   * Creates a generator wrapping a function from `T` to `(K, V)`. Then this tuple is returned in a
   * singlet `Map`.
   * @param f
   *   the mapping function
   * @return
   *   a generator from `T` to a singlet `Map[K, V]`.
   */
  def makeEntry[T, K, V](f: T => (K, V)): Generator[T, Map[K, V]] = instance(t => Map(f(t)))
}
