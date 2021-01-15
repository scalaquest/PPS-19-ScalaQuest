package io.github.scalaquest.core.parsing.scalog.dsl

import io.github.scalaquest.core.parsing.scalog.{Atom, Compound, Term}

/**
 * A `CompoundBuilder` allows the generation of compound terms using a
 * Prolog-like syntax that is checked by the scala compiler.
 *
 * @param functor the atom used as a functor by the generated compound terms
 *
 * ==Overview==
 *  For example if you run this code:
 *  {{{
 *    val hello = CompoundBuilder(Atom("hello"))
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
