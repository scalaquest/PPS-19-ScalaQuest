/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.parsing.scalog

/** Unit of information ending with the full-stop. */
sealed trait Clause extends CodeGen

/** A special case of clause with empty body. */
case class Fact(head: Term) extends Clause {

  /**
   * Allows the creation of a [[DCGRule]].
   * @param right
   *   the right side of the operation
   * @return
   *   the resulting DCG rule.
   */
  def -->(right: Term): DCGRule = DCGRule(head, right)

  /**
   * Allows the creation of a [[Rule]].
   * @param right
   *   the right side of the operation
   * @return
   *   the resulting rule.
   */
  def :-(right: Term): Rule = Rule(head, right)

  override def generate: String = s"${head.generate}."
}

/**
 * A horn clause. It is read `head` is true if `body` is true.
 *
 * The following example:
 * {{{
 *   Rule(Compound(Atom("hello"), Atom("world")), Atom("hello")).generate
 * }}}
 *
 * Should result in the following Prolog clause being printed:
 * {{{
 *   hello(world) :- hello.
 * }}}
 *
 * @param head
 *   the left term
 * @param body
 *   the right term
 */
case class Rule(head: Term, body: Term) extends Clause {
  override def generate: String = s"${head.generate} :- ${body.generate}."
}

/**
 * A definite clause grammar rule.
 *
 * The following example:
 * {{{
 *   DCGRule(Compound(Atom("hello"), Atom("world")), ListP(Atom("hello"), Atom("world"))).generate
 * }}}
 *
 * Should result in the following Prolog clause being printed:
 * {{{
 *   hello(world) --> ["hello","world"].
 * }}}
 *
 * @param left
 *   the left term
 * @param right
 *   the right term
 */
case class DCGRule(left: Term, right: Term) extends Clause {
  override def generate: String = s"${left.generate} --> ${right.generate}."
}
