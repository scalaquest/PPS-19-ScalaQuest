package io.github.scalaquest.core.parsing.scalog.dsl

import io.github.scalaquest.core.parsing.scalog.{Compound, Term}

/**
 * A `Constructor` allows the generation of compound terms using a Prolog-like syntax that is
 * checked by the scala compiler.
 */
trait Constructor extends CompoundBase {

  /** Accessor for the method `apply`. */
  object constructor {

    /**
     * Allows to simulate the Prolog compound term declaration.
     * @param arg
     *   the first mandatory term
     * @param args
     *   the other optional terms
     * @return
     *   a [[Compound]] with the given arguments.
     */
    def apply(arg: Term, args: Term*): Compound = Compound(functor, arg, args.toList)
  }
}
