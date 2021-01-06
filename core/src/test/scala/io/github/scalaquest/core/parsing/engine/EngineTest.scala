package io.github.scalaquest.core.parsing.engine

import alice.tuprolog.{Prolog, Theory, Term => TuPrologTerm}
import org.scalatest.PrivateMethodTester.PrivateMethod
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.PrivateMethodTester._

class EngineTest extends AnyWordSpec {

  val prolog: Prolog = new Prolog()
  prolog.setTheory(
    new Theory("""
               |ciao(goro).
               |ciao(filo).
               |ciao(riechi).
               |ciao(uoomo).
               |""".stripMargin)
  )

  val engine: PrologEngine = PrologEngine(prolog)

  "The engine" when {
    "provided any query" should {
      "never throw exception" in {
        val term: Term = Compound(Atom("ciao"), Variable("X"))
        assert(engine.query(term).flatMap(_.getTerm(Variable("X"))).collect { case Atom(name) => name } == Seq(
          "goro",
          "filo",
          "riechi",
          "uoomo"
        ))
      }
    }
  }
}
