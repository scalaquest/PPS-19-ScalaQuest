package io.github.scalaquest.core.parsing.engine.tuprolog

import alice.tuprolog.{NoSolutionException, SolveInfo, UnknownVarException}
import io.github.scalaquest.core.parsing.engine.Solution
import io.github.scalaquest.core.parsing.scalog.{Term, Variable}

/** Wrapper for a tuProlog solution. */
private[tuprolog] case class TuPrologSolution(solveInfo: SolveInfo) extends Solution {
  import implicits.EnhancedTuPrologTerm

  override def body: Term = solveInfo.getSolution.toTerm

  override def getVariable(variable: Variable): Option[Term] = {
    try {
      Some(solveInfo.getTerm(variable.name).toTerm)
    } catch {
      case _: UnknownVarException => None
      case _: NoSolutionException => None
    }
  }
}
