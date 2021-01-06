package io.github.scalaquest.core.parsing.engine

import alice.tuprolog
import alice.tuprolog.lib.{DCGLibrary => TuPrologDCGLibrary}
import alice.tuprolog.{
  SolveInfo,
  Struct,
  Theory,
  Var,
  Library => TuPrologLibrary,
  Prolog => TuProlog,
  Term => TuPrologTerm
}

import scala.collection.immutable.{AbstractSeq, LinearSeq}
import scala.collection.mutable.ListBuffer

sealed trait Library {
  def ref: TuPrologLibrary
}

case object DCGLibrary extends Library {
  override def ref: TuPrologLibrary = new TuPrologDCGLibrary
}

object Engine {

  implicit class EnhancedTerm(term: Term) {
    def toTuPrologTerm: TuPrologTerm = TuPrologTerm.createTerm(term.generate)
  }

  private def createEngine(theory: Theory, libraries: Seq[Library]): TuProlog = {
    val prolog = new TuProlog
    prolog.setTheory(theory)
    libraries.foreach(l => prolog.loadLibrary(l.ref))
    prolog
  }

  def apply(theory: Theory, libraries: Seq[Library] = Seq()): Engine = PrologEngine(createEngine(theory, libraries))
}

trait Engine {
  def query(term: Term): Seq[Solution]
}

trait Solution {
  def getTerm(variable: Variable): Option[Term]
}

case class SolutionImpl(solveInfo: SolveInfo) extends Solution {

  def tuPrologTermToTerm(tuPrologTerm: TuPrologTerm): Option[Term] = {
    tuPrologTerm match {
      case number: tuprolog.Number => Some(Number(number.intValue))
      case struct: Struct => struct match {
          case s if s.isCompound => Some(buildCompound(s))
          case a                 => Some(Atom(a.getName))
        }
      case _var: Var => Some(Variable(_var.getName))
      case _         => None
    }
  }

  private def getArgs(struct: Struct): Seq[Term] = {
    (0 until struct.getArity)
      .map(i => struct.getTerm(i))
      .flatMap(tuPrologTermToTerm)
  }

  private def buildCompound(struct: Struct): Compound = {
    getArgs(struct) match {
      case h :: t =>
        Compound(Atom(struct.getName), h, t)
    }
  }

  override def getTerm(variable: Variable): Option[Term] = {
    try {
      tuPrologTermToTerm(solveInfo.getTerm(variable.name))
    } catch {
      case _: Exception => None
    }
  }
}

case class PrologEngine(prolog: TuProlog) extends Engine {
  import Engine.EnhancedTerm

//  def prolog: TuProlog
//  private def exploreSolutionsUgly(goal: TuPrologTerm): Seq[SolveInfo] = {
//    var info     = prolog.solve(goal)
//    var buffer   = ListBuffer[SolveInfo]()
//    var continue = true
//    while (info.isSuccess && continue) {
//      println(info.getSolution)
//      buffer += info
//      if (prolog.hasOpenAlternatives) {
//        info = prolog.solveNext()
//      } else {
//        continue = false
//      }
//    }
//    buffer.toSeq
//  }

  private def exploreSolutions(goal: TuPrologTerm): Seq[SolveInfo] = {
    @scala.annotation.tailrec
    def old(info: SolveInfo, acc: Seq[SolveInfo]): Seq[SolveInfo] = {
      if (info.isSuccess) {
        val res = acc :+ info
        if (prolog.hasOpenAlternatives) old(prolog.solveNext(), res) else res
      } else {
        acc
      }
    }

    def go(info: SolveInfo): LazyList[SolveInfo] = {
      val current = if (info.isSuccess) LazyList(info) else LazyList.empty
      val next    = if (prolog.hasOpenAlternatives) go(prolog.solveNext()) else LazyList.empty
      current #::: next
    }

    go(prolog.solve(goal))
  }

  override def query(term: Term): Seq[SolutionImpl] = {
    val solveInfo = exploreSolutions(term.toTuPrologTerm)
    solveInfo.map(SolutionImpl)
  }
}
