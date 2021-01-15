package io.github.scalaquest.core.parsing.scalog

/**
 * This package includes some facility methods in order to create terms in a
 * Prolog-like syntax.
 *
 * ==Overview==
 * If you include implicit conversions and [[dsl.CompoundBuilder]] you can
 * create terms as in this example:
 * {{{
 *   val hello = CompoundBuilder("hello")
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

  /**
   * A `CompoundBuilder` allows the generation of compound terms using a
   * Prolog-like syntax that is checked by the scala compiler.
   * @param functor the atom used as a functor by the generated compound terms
   *
   *  Example:
   *  {{{
   *    val hello = CompoundTerm(Atom("hello"))
   *    (hello(Atom("darkness"), Atom("my"), Atom("old"), Atom("friend"))).generate
   *  }}}
   *
   *  Should result in the following compound term being printed:
   *  {{{
   *    hello(darkness, my, old, friend)
   *  }}}
   */
  case class CompoundBuilder(functor: Atom) {

    /**
     * Allows to simulate the Prolog compound term declaration.
     * @param arg the first mandatory term
     * @param args the other optional terms
     * @return a [[Compound]] which has `functor` as functor and the arguments
     *         as the compound term arguments.
     */
    def apply(arg: Term, args: Term*): Compound = Compound(functor, arg, args.toList)
  }

  /** Enables the implicit conversion from `String` to `Atom` */
  implicit def stringToAtom(name: String): Atom = Atom(name)

  /** Enables the implicit conversion from `Int` to `Number` */
  implicit def intToNumber(n: Int): Number = Number(n)

  /** Enables the implicit conversion from `Term` to `Fact` */
  implicit def termToFact(term: Term): Fact = Fact(term)

  /** Enables the implicit conversion fron `Seq[Term]` to `ListP` */
  implicit def seqToListP(seq: Seq[Term]): ListP = ListP(seq: _*)

}
