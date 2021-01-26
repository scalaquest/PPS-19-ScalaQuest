package io.github.scalaquest.core.parsing

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.parsing.scalog.{Atom, CodeGen, Term, Variable}

import scala.annotation.tailrec

sealed trait Verb {

  def name: String

  def action: Action

  def clause: CodeGen

  def atom: Atom = Atom(name.replace(" ", "_"))

  def tokens: Seq[Term] = name.split(" ") map Atom
}

object Verb {
  import io.github.scalaquest.core.parsing.engine._
  import io.github.scalaquest.core.parsing.scalog.dsl._

  def betaReduce(functor: String, variablesNum: Int): Term = {
    @tailrec
    def go(variables: List[Variable], term: Term): Term =
      variables match {
        case h :: t => go(t, h ^: term)
        case Nil    => term
      }
    val f         = CompoundBuilder(functor)
    val variables = ('A' to 'Z' map (_.toString) map Variable take variablesNum).toList
    val right = variables match {
      case h :: Nil => f(h)
      case h :: t   => f(h, t: _*)
    }
    go(variables, right)
  }

  implicit class AllowBetaReduction(functor: String) {
    def betaReduce(variablesNumber: Int): Term = Verb.betaReduce(functor, variablesNumber)
  }

  val iv = CompoundBuilder("iv")
  val tv = CompoundBuilder("tv")
  val v  = CompoundBuilder("v")

  case class Intransitive(name: String, action: Action) extends Verb {
    override def clause: CodeGen = iv(atom) --> tokens
  }

  case class Transitive(name: String, action: Action) extends Verb {
    override def clause: CodeGen = tv(atom.name betaReduce 2) --> tokens
  }

  case class Ditransitive(name: String, action: Action, prep: String) extends Verb {
    override def clause: CodeGen = v("3/to", atom.name betaReduce 3) --> tokens
    //override def clause: CodeGen = v(3 / prep, atom.name betaReduce 3) --> tokens
  }
}
/*
object Example extends App {

  // Stanze
  // ...

  import io.github.scalaquest.core.model.impl.SimpleModel.SimpleItem

  // Verbi
  val generator = GrammarGenerator(
    Verb.Transitive("use", Actions.Use),
    Verb.Transitive("take", Actions.Take),
    Verb.Transitive("pick up", Actions.Take),
    Verb.Intransitive("inspect", Actions.Inspect),
    Verb.Intransitive("look around", Actions.Inspect),
    Verb.Ditransitive("give", Actions.Give)
  ) compose ItemGenerator(
    new SimpleItem {
      override def name: String = "apple"
    },
    new SimpleItem {
      override def name: String = "key"
    }
  )

  println(generator.generate)

}
 */
