package io.github.scalaquest.core.parsing.engine

/** A Prolog clause. */
sealed trait Clause {

  /** String representation of the `Clause`. */
  def generate: String
}

/** A Prolog term. */
sealed trait Term extends Clause {

  /**
   * Template method for all infix operators.
   *
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

//  def :-(right: Term): Rule = Rule(body, right)

  override def generate: String = s"${body.generate}."
}

//case class Rule(head: Term, body: Term) extends Clause {
//  override def generate: String = s"${head.generate} :- ${body.generate}."
//}

/**
 * A declarative clause grammar rule.
 * @param left the left term
 * @param right the right term
 *
 * Example:
 * {{{
 *   DCGRule(Compound(Atom("ciao"), Atom("mondo")), ListP(Atom("ciao"), Atom("mondo"))).generate
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

/** This object is mimicking a package declaration. */
object clause {

  /**
   * This package includes some facility methods in order to create terms in a
   * Prolog-like syntax.
   *
   * Example:
   * {{{
   *   val hello = CompoundBuilder("hello")
   *   val X = Variable("X")
   *   (hello(X) --> ListP("hello", X)).generate
   * }}}
   *
   * Should result in the following Prolog clause being printed:
   * {{{
   *   hello(X) --> ["hello", X].
   * }}}
   */
  object dsl {

    /**
     * A `CompoundBuilder` allows the generation of compound terms using a
     * Prolog-like syntax that is checked by the scala compiler.
     * @param functor the atom used as a functor by the generated compound terms
     *
     *  Example:
     *  {{{
     *    val hello = CompoundTerm(Atom("hello"))
     *    (hello(Atom("darkness"), Atom("my"), Atom("old"), Atom("friend"))).generate
     *  }}}
     *
     *  Should result in the following compound term being printed:
     *  {{{
     *    hello(darkness, my, old, friend)
     *  }}}
     */
    case class CompoundBuilder(functor: Atom) {

      /**
       * This method allows to simulate the Prolog compound term declaration.
       * @param arg the first mandatory term
       * @param args the other optional terms
       * @return a [[Compound]] which has `functor` as functor and the arguments
       *         as the compound term arguments.
       */
      def apply(arg: Term, args: Term*): Compound = Compound(functor, arg, args.toList)
    }

    implicit def stringToAtom(name: String): Atom  = Atom(name)
    implicit def intToNumber(n: Int): Number       = Number(n)
    implicit def termToFact(term: Term): Fact      = Fact(term)
    implicit def seqToListP(seq: Seq[Term]): ListP = ListP(seq: _*)

  }
}
