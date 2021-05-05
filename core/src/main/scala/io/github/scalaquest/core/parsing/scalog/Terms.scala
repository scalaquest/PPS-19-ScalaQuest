/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.parsing.scalog

/** A Prolog data object. */
sealed trait Term extends CodeGen {

  /**
   * Template method for all infix operators.
   * @note
   *   All operators that use this method should be right associative.
   */
  protected def infixOp(op: String)(left: Term): Compound = Compound(Atom(op), left, List(this))

  /** Implementation of the infix `^` Prolog operator. */
  def ^:(left: Term): Compound = infixOp("^")(left)

  /** Implementation of the infix `/` Prolog operator. */
  def /:(left: Term): Compound = infixOp("/")(left)
}

/** A Prolog atom. */
case class Atom(name: String) extends Term {
  override def generate: String = name
}

/** A Prolog number */
case class Number(n: Int) extends Term {
  override def generate: String = n.toString
}

/** A Prolog variable. */
case class Variable(name: String) extends Term {
  override def generate: String = name
}

/**
 * A Prolog compound term.
 * @param functor
 *   the atom used as a functor
 * @param arg1
 *   the first mandatory term
 * @param args
 *   the other optional terms
 */
case class Compound(functor: Atom, arg1: Term, args: List[Term] = List()) extends Term {

  override def generate: String =
    s"${functor.generate}(${(arg1 +: args).map(_.generate).mkString(",")})"
}

/** A Prolog list. */
case class ListP(terms: Term*) extends Term {
  override def generate: String = terms.map(_.generate).mkString("[", ",", "]")
}
