package io.github.scalaquest.core.parsing.engine.tuprolog

import io.github.scalaquest.core.parsing.engine.exceptions.InvalidTheoryException
import io.github.scalaquest.core.parsing.engine._
import io.github.scalaquest.core.parsing.scalog.{Atom, Compound, Number, Variable}
import org.scalatest.Inspectors._
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source

class TuPrologEngineTest extends AnyWordSpec {

  val people = Seq(
    "alessio",
    "boris",
    "claudio",
    "daria"
  )

  val theory = Theory("""
               |male(alessio).
               |male(boris).
               |male(claudio).
               |female(daria).
               |human(X) :- male(X).
               |human(X) :- female(X).
               |""".stripMargin)

  val engine: Engine = TuPrologEngine(theory)

  "The engine" when {
    "provided a theory" should {
      "accept if it's correct" in {
        val theory = Source.fromResource("base.pl").mkString
        TuPrologEngine(Theory(theory))
        succeed
      }
      "throw if it is invalid" in {
        assertThrows[InvalidTheoryException] {
          val theory = "some invalid theory"
          TuPrologEngine(Theory(theory))
        }
      }
    }
    "provided a query with a single result" should {
      "return it as a unary sequence" in {
        val term = Compound(Atom("male"), Atom("boris"))
        val res  = engine.solve(term)
        assert(res.size == 1)
        assert(res.head.body == term)
      }
    }
    "provided a query with multiple results" should {
      "return them as a sequence" in {
        val term = Compound(Atom("human"), Variable("X"))
        val res  = engine.solve(term)
        forAll(res.zip(people)) { case (sol, name) =>
          assert(sol.body == term.copy(arg1 = Atom(name)))
        }
      }
    }
    "provided any query" should {
      "resolve variables with their values" in {
        val term = Compound(Atom("human"), Variable("X"))
        val res  = engine.solve(term)
        forAll(res.zip(people)) { case (sol, name) =>
          assert(sol.getVariable(Variable("X")).contains(Atom(name)))
        }
      }
      "resolve unknown variables with empty" in {
        val term = Compound(Atom("human"), Variable("X"))
        val res  = engine.solve(term)
        assert(res.nonEmpty)
        forAll(res) { sol =>
          assert(sol.getVariable(Variable("Y")).isEmpty)
        }
      }
    }
  }

  "Implicit tuProlog terms conversion" when {
    import implicits.EnhancedTuPrologTerm
    "passed a tuProlog atom" should {
      "convert it to an Atom" in {
        val atom = alice.tuprolog.Term.createTerm("hello")
        assert(atom.isAtom)
        assert(atom.toTerm == Atom("hello"))
      }
    }
    "passed a tuProlog compound" should {
      "convert it to a Compound" in {
        val compound = alice.tuprolog.Term.createTerm("hello(world)")
        assert(compound.isCompound)
        assert(compound.toTerm == Compound(Atom("hello"), Atom("world")))
      }
    }
    "passed a tuProlog number" should {
      "convert it to a Number" in {
        val number = new alice.tuprolog.Int(5)
        assert(number.toTerm == Number(5))
      }
    }
  }

  "Implicit term conversion" when {
    import implicits.EnhancedTerm
    "provided an Atom" should {
      "create a tuProlog atom" in {
        val atom = Atom("hello")
        assert(atom.toTuPrologTerm.isEqual(new alice.tuprolog.Struct("hello")))
      }
    }
    "provided a Number" should {
      "create a tuProlog number" in {
        val number = Number(5)
        assert(number.toTuPrologTerm.isEqual(new alice.tuprolog.Int(5)))
      }
    }
    "provided a Compound" should {
      "create a tuProlog compound" in {
        val compound = Compound(Atom("hello"), Atom("world"))
        assert(compound.toTuPrologTerm.isEqual(new alice.tuprolog.Struct("hello", new alice.tuprolog.Struct("world"))))
      }
    }
  }
}
