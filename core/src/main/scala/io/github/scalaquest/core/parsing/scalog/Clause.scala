package io.github.scalaquest.core.parsing.scalog

/** A Prolog clause. */
sealed trait Clause {

  /** String representation of the `Clause`. */
  def generate: String
}

/** A Prolog term. */
sealed trait Term extends Clause {

  /**
   * Template method for all infix operators.
   * @note All operators that use this method should be right associative.
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
 * @param functor the atom used as a functor
 * @param arg1 the first mandatory term
 * @param args the other optional terms
 */
case class Compound(functor: Atom, arg1: Term, args: List[Term] = List()) extends Term {
  override def generate: String = s"${functor.generate}(${(arg1 +: args).map(_.generate).mkString(",")})"
}

/** A Prolog list. */
case class ListP(terms: Term*) extends Term {
  override def generate: String = terms.map(_.generate).mkString("[", ",", "]")
}

/** A Prolog fact. */
case class Fact(body: Term) extends Clause {

  /**
   * Allows the creation of a [[DCGRule]].
   * @param right the right side of the operation
   * @return the resulting DCG rule.
   */
  def -->(right: Term): DCGRule = DCGRule(body, right)

  /**
   * Allows the creation of a [[Rule]].
   * @param right the right side of the operation
   * @return the resulting rule.
   */
  def :-(right: Term): Rule = Rule(body, right)

  override def generate: String = s"${body.generate}."
}

/**
 * A Prolog rule.
 *
 * @param head the left term
 * @param body the right term
 *
 * ==Overview==
 * The following example:
 * {{{
 *   Rule(Compound(Atom("hello"), Atom("world")), Atom("hello")).generate
 * }}}
 *
 * Should result in the following Prolog clause being printed:
 * {{{
 *   hello(world) :- hello.
 * }}}
 */
case class Rule(head: Term, body: Term) extends Clause {
  override def generate: String = s"${head.generate} :- ${body.generate}."
}

/**
 * A declarative clause grammar rule.
 * @param left the left term
 * @param right the right term
 *
 * ==Overview==
 * The following example:
 * {{{
 *   DCGRule(Compound(Atom("hello"), Atom("world")), ListP(Atom("hello"), Atom("world"))).generate
 * }}}
 *
 * Should result in the following Prolog clause being printed:
 * {{{
 *   hello(world) --> ["hello","world"].
 * }}}
 */
case class DCGRule(left: Term, right: Term) extends Clause {
  override def generate: String = s"${left.generate} --> ${right.generate}."
}
