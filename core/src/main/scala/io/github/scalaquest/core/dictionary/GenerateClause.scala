package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.parsing.scalog.dsl.CompoundBuilder
import io.github.scalaquest.core.parsing.scalog.{Atom, Clause, Term, Variable}

import scala.annotation.tailrec

trait GenerateClause {
  def clause: Clause
}

trait ClauseUtils extends GenerateClause with Utils { self: BaseVerb =>

  implicit class AllowBetaReductionOnAtom(functor: Atom) {
    def bReduce(variablesNumber: Int): Term = ClauseUtils.bReduce(functor.name, variablesNumber)
  }

  def atom: Atom = Atom(escapedName)

  def tokens: List[Term] = name.split(" ").map(Atom).toList
}

object ClauseUtils {

  /**
   * Generates a structure ready to perform a beta reduction.
   *
   * Example:
   * {{{
   *   bReduce("take", 2)
   * }}}
   *
   * Should result in the following being printed:
   * {{{
   *   ^(A, ^(B, take(B, A))
   * }}}
   *
   * @param functor
   *   the functor name
   * @param variablesNum
   *   the number of variables
   * @return
   *   a term ready to perform a beta reduction with the given number of terms.
   */
  def bReduce(functor: String, variablesNum: Int): Term = {
    @tailrec
    def go(variables: List[Variable], term: Term): Term =
      variables match {
        case h :: t => go(t, h ^: term)
        case Nil    => term
      }
    val f         = CompoundBuilder(Atom(functor))
    val variables = ('A' to 'Z' map (_.toString) map Variable take variablesNum).toList
    val right = variables match {
      case h :: Nil => f(h)
      case h :: t   => f(h, t: _*)
    }
    go(variables, right)
  }
}
