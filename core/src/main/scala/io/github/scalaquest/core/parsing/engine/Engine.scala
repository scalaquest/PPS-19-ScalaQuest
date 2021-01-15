package io.github.scalaquest.core.parsing.engine

import io.github.scalaquest.core.parsing.engine.tuprolog.TuPrologEngine
import io.github.scalaquest.core.parsing.scalog.{Term, Variable}

/**
 * A data structure that contains the result of a Prolog query being evaluated
 * as true.
 */
trait Solution {

  /** Returns the solution as a [[Term]]. */
  def body: Term

  /**
   * Allows to retrieve the value of a given variable in the substitution.
   * @param variable the variable to look for
   * @return optionally, the value of the variable in the substitution.
   */
  def getVariable(variable: Variable): Option[Term]
}

/**
 * A generic Prolog engine. It allows to execute queries and collect the results.
 *
 * Example:
 * {{{
 *   val theory = Theory(
 *     "hello(mario)." +
 *     "hello(luigi)." +
 *     "hello(peach)." +
 *     "hello(daisy)."
 *   )
 *
 *   val characters = for {
 *     s <- Engine(theory).solve(Compound(Atom("hello"), Variable("X")))
 *     x <- s.getVariable(Variable("X"))
 *   } yield x
 *
 *   assert(characters == Seq("mario", "luigi", "peach", "daisy")) // true
 * }}}
 */
trait Engine {

  /**
   * Allows to query the engine for a term, it will return a sequence of
   * solutions being evaluated as true.
   * @param term the argument of the query
   * @return the sequence of positive solutions.
   */
  def solve(term: Term): Seq[Solution]
}

object Engine {

  /** Creates an [[Engine]] with the provided theory and libraries. */
  def apply(theory: Theory, libraries: Set[Library] = Set()): Engine = TuPrologEngine(theory, libraries)
}
