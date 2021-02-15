package io.github.scalaquest.core.parsing.engine.tuprolog

import alice.tuprolog
import alice.tuprolog.{
  SolveInfo,
  Struct,
  Var,
  Prolog => TuProlog,
  Term => TuPrologTerm,
  Theory => TuPrologTheory
}
import io.github.scalaquest.core.parsing.engine.exceptions.InvalidTheoryException
import io.github.scalaquest.core.parsing.engine.tuprolog.TuPrologEngine.{buildCompound, buildNumber}
import io.github.scalaquest.core.parsing.engine.{Engine, Library, Solution, Theory}
import io.github.scalaquest.core.parsing.scalog
import io.github.scalaquest.core.parsing.scalog._

import scala.annotation.nowarn

/**
 * Engine implementation that uses a tuProlog engine under the hood.
 * @note
 *   this implementation computes solutions in a lazy way
 */
private class TuPrologEngine(prolog: TuProlog) extends Engine {
  import implicits._

  private def exploreSolutions(goal: TuPrologTerm): Seq[SolveInfo] = {
    def go(info: SolveInfo): LazyList[SolveInfo] = {
      val current =
        if (info.isSuccess) LazyList(info)
        else LazyList.empty
      lazy val next = if (prolog.hasOpenAlternatives) go(prolog.solveNext()) else LazyList.empty
      current #::: next
    }

    go(prolog.solve(goal))
  }

  override def solve(term: Term): Seq[Solution] =
    exploreSolutions(term.toTuPrologTerm).map(TuPrologSolution)
}

object TuPrologEngine {
  import implicits._

  /**
   * Creates an [[Engine]] with the provided theory and libraries using tuProlog under the hood.
   * @note
   *   this implementation computes solutions lazily.
   */
  def apply(theory: Theory, libraries: Set[Library] = Set()): Engine =
    new TuPrologEngine(createTuProlog(theory.toTuProlog, libraries))

  private def getArgs(struct: Struct): Seq[Term] = {
    (0 until struct.getArity)
      .map(i => struct.getTerm(i))
      .map(_.toTerm)
  }

  @nowarn("msg=exhaustive")
  /**
   * @note
   *   this will fail if called with a Struct with no arguments.
   */
  private[tuprolog] def buildCompound(struct: Struct): Compound =
    getArgs(struct).toList match {
      case h :: t =>
        Compound(Atom(struct.getName), h, t)
    }

  /**
   * @note
   *   this might fail if not called with an Integer, but for now we don't use any other Number
   *   implementation
   */
  private[tuprolog] def buildNumber(number: tuprolog.Number): scalog.Number =
    Number(number.intValue)

  /**
   * @note
   *   this might throw if called with an invalid theory
   */
  private def createTuProlog(theory: TuPrologTheory, libraries: Set[Library]): TuProlog = {
    val prolog = new TuProlog
    try {
      prolog.setTheory(theory)
    } catch {
      case e: alice.tuprolog.InvalidTheoryException => throw InvalidTheoryException(e)
    }
    libraries.foreach(l => prolog.loadLibrary(l.toTuProlog))
    prolog
  }
}

private[tuprolog] object implicits {

  implicit class EnhancedTerm(term: Term) {
    def toTuPrologTerm: TuPrologTerm = TuPrologTerm.createTerm(term.generate)
  }

  implicit class EnhancedTuPrologTerm(tuPrologTerm: TuPrologTerm) {

    @nowarn("msg=match.*exhaustive")
    def toTerm: Term =
      tuPrologTerm match {
        case number: tuprolog.Number => buildNumber(number)
        case struct: Struct =>
          struct match {
            case s if s.isCompound => buildCompound(s)
            case a                 => Atom(a.getName)
          }
        case _var: Var => Variable(_var.getName)
      }
  }
}
