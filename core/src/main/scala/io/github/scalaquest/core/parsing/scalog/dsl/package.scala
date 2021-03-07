package io.github.scalaquest.core.parsing.scalog

/**
 * This package includes some facility methods in order to create terms in a Prolog-like syntax.
 *
 * If you include implicit conversions and [[dsl.CompoundBuilder]] you can create terms as in this
 * example:
 * {{{
 *   val hello = CompoundBuilder("hello").constructor
 *   val X = Variable("X")
 *   (hello(X) --> ListP("hello", X)).generate
 * }}}
 *
 * Should result in the following Prolog clause being printed:
 * {{{
 *   hello(X) --> ["hello", X].
 * }}}
 */
package object dsl {

  /** Enables the implicit conversion from `String` to `Atom` */
  implicit def stringToAtom(name: String): Atom = Atom(name)

  /** Enables the implicit conversion from `Int` to `Number` */
  implicit def intToNumber(n: Int): Number = Number(n)

  /** Enables the implicit conversion from `Term` to `Fact` */
  implicit def termToFact(term: Term): Fact = Fact(term)

  /** Enables the implicit conversion fron `Seq[Term]` to `ListP` */
  implicit def seqToListP(seq: Seq[Term]): ListP = ListP(seq: _*)

}
