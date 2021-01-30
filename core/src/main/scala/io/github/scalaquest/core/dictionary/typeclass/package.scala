package io.github.scalaquest.core.dictionary

package object typeclass {
  // [A, B] => Generator[A, B]
  type GeneratorLambda[A, B] = ({ type L[A] = Generator[A, B] })
  // [A]    => Generator[A, Program]
  type ProgramGenerator[A] = GeneratorLambda[A, Program]
}
