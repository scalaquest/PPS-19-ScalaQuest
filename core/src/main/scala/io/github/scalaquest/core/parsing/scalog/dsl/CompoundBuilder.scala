package io.github.scalaquest.core.parsing.scalog.dsl

import io.github.scalaquest.core.parsing.scalog.{Atom, Compound, Term}

abstract class CompoundBase {
  def functor: Atom
}

/**
 * A `CompoundBuilder` allows the generation of compound terms using a Prolog-like syntax that is
 * checked by the scala compiler.
 *
 * For example if you run this code:
 * {{{
 *   val hello = CompoundBuilder(Atom("hello"))
 *   (hello(Atom("darkness"), Atom("my"), Atom("old"), Atom("friend"))).generate
 * }}}
 *
 * Should result in the following compound term being printed:
 * {{{
 *   hello(darkness, my, old, friend)
 * }}}
 *
 * @param functor
 *   the atom used as a functor by the generated compound terms
 */
case class CompoundBuilder(functor: Atom) extends CompoundBase with Extractors with Constructor
