package io.github.scalaquest.core.parsing.engine

import alice.tuprolog
import alice.tuprolog.{
  NoSolutionException,
  SolveInfo,
  Struct,
  Theory,
  UnknownVarException,
  Var,
  Prolog => TuProlog,
  Term => TuPrologTerm
}

trait Solution {
  def body: Term
  def getVariable(variable: Variable): Option[Term]
}

trait Engine {
  def query(term: Term): Seq[Solution]
}

object Engine {

  implicit class EnhancedTerm(term: Term) {
    def toTuPrologTerm: TuPrologTerm = TuPrologTerm.createTerm(term.generate)
  }

  implicit class EnhancedTuPrologTerm(tuPrologTerm: TuPrologTerm) {

    def toTerm: Term =
      tuPrologTerm match {
        case number: tuprolog.Number => buildNumber(number)
        case struct: Struct => struct match {
            case s if s.isCompound => buildCompound(s)
            case a                 => Atom(a.getName)
          }
        case _var: Var => Variable(_var.getName)
      }
  }

  private def getArgs(struct: Struct): Seq[Term] = {
    (0 until struct.getArity)
      .map(i => struct.getTerm(i))
      .map(_.toTerm)
  }

  /*
   * Notice this will fail if called with a Struct with no arguments.
   */
  private def buildCompound(struct: Struct): Compound = {
    getArgs(struct).toList match {
      case h :: t =>
        Compound(Atom(struct.getName), h, t)
    }
  }

  /*
   * Notice this will fail if not called with an Integer, but for now we don't
   * use any other Number implementation
   */
  private def buildNumber(number: tuprolog.Number): Number = Number(number.intValue)

  private def createTuProlog(theory: Theory, libraries: Seq[Library]): TuProlog = {
    val prolog = new TuProlog
    prolog.setTheory(theory)
    libraries.foreach(l => prolog.loadLibrary(l.ref))
    prolog
  }

  def apply(theory: Theory, libraries: Seq[Library] = Seq()): Engine = PrologEngine(createTuProlog(theory, libraries))
}

case class SolutionImpl(solveInfo: SolveInfo) extends Solution {
  import Engine.EnhancedTuPrologTerm

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

case class PrologEngine(prolog: TuProlog) extends Engine {
  import Engine.EnhancedTerm

  private def exploreSolutions(goal: TuPrologTerm): Seq[SolveInfo] = {
    def go(info: SolveInfo): LazyList[SolveInfo] = {
      val current =
        if (info.isSuccess) LazyList(info)
        else LazyList()
      lazy val next = if (prolog.hasOpenAlternatives) go(prolog.solveNext()) else LazyList()
      current #::: next
    }

    go(prolog.solve(goal))
  }

  override def query(term: Term): Seq[Solution] = {
    exploreSolutions(term.toTuPrologTerm).map(SolutionImpl)
  }
}
