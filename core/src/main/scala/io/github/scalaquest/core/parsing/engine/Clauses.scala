package io.github.scalaquest.core.parsing.engine

sealed trait Clause {
  def generate: String
}

sealed trait Term extends Clause {
  protected def infixOp(op: String)(left: Term): Compound = Compound(Atom(op), left, List(this))

  def ^:(left: Term): Compound = infixOp("^")(left)
}

case class Atom(name: String) extends Term {
  override def generate: String = name
}

case class Number(n: Int) extends Term {
  override def generate: String = n.toString
}

case class Variable(name: String) extends Term {
  override def generate: String = name
}

case class Compound(functor: Atom, arg1: Term, args: List[Term] = List()) extends Term {
  override def generate: String = s"${functor.generate}(${(arg1 +: args).map(_.generate).mkString(",")})"
}

case class ListP(terms: Term*) extends Term {
  override def generate: String = terms.map(_.generate).mkString("[", ",", "]")
}

case class Fact(body: Term) extends Clause {
  def -->(right: Term): DCGRule = DCGRule(body, right)

//  def :-(right: Term): Rule = Rule(body, right)

  override def generate: String = s"${body.generate}."
}

//case class Rule(head: Term, body: Term) extends Clause {
//  override def generate: String = s"${head.generate} :- ${body.generate}."
//}

case class DCGRule(left: Term, right: Term) extends Clause {
  override def generate: String = s"${left.generate} --> ${right.generate}."
}

object dsl {

  case class CompoundBuilder(functor: Atom) {
    def apply(arg: Term, args: Term*): Compound = Compound(functor, arg, args.toList)
  }

  implicit def stringToAtom(name: String): Atom  = Atom(name)
  implicit def intToNumber(n: Int): Number       = Number(n)
  implicit def termToFact(term: Term): Fact      = Fact(term)
  implicit def seqToListP(seq: Seq[Term]): ListP = ListP(seq: _*)

}
