package io.github.scalaquest.core.parsing.scalog

import org.scalatest.wordspec.AnyWordSpec

class ClausesTest extends AnyWordSpec {

  "An atom" should {
    "generate its content" in {
      val name = "some name"
      assert(Atom(name).generate == name)
    }
  }

  "A number" should {
    "generate its value as a string" in {
      val value = 7
      assert(Number(value).generate == value.toString)
    }
  }

  "A variable" should {
    "generate its name" in {
      val name = "some other name"
      assert(Variable(name).generate == name)
    }
  }

  "A compound term" should {
    "generate functor and an argument surrounded by parenthesis" in {
      val compound = Compound(Atom("hello"), Atom("world"), List())
      assert(compound.generate == "hello(world)")
    }

    "separate its arguments using commas" in {
      val compound =
        Compound(Atom("hello"), Atom("darkness"), List(Atom("my"), Atom("old"), Atom("friend")))
      assert(compound.generate == "hello(darkness,my,old,friend)")
    }
  }

  "A ListP" should {
    "generate an empty list" in {
      assert(ListP().generate == "[]")
    }

    "generate an unary list" in {
      assert(ListP(Number(1)).generate == "[1]")
    }

    "generate a list with more than 1 element" in {
      assert(ListP(1 to 5 map Number: _*).generate == "[1,2,3,4,5]")
    }
  }

  "A fact" should {
    "generate its body followed by a period" in {
      assert(Fact(Compound(Atom("hello"), Atom("world"))).generate == "hello(world).")
    }

    "be able to create a rule" in {
      val rule = Fact(Compound(Atom("hello"), Atom("world"))) :- Atom("something")
      assert(rule == Rule(Compound(Atom("hello"), Atom("world")), Atom("something")))
    }

    "be able to create a DCG rule" in {
      val dcgRule = Fact(Compound(Atom("hello"), Atom("world"))) --> Atom("something")
      assert(dcgRule == DCGRule(Compound(Atom("hello"), Atom("world")), Atom("something")))
    }
  }

  "A rule" should {
    "generate its left member and its right member" in {
      val rule = Fact(Compound(Atom("hello"), Atom("world"))) :- Atom("something")
      assert(rule.generate == "hello(world) :- something.")
    }
  }

  "A DCG rule" should {
    "generate its left member and its right member" in {
      val dcgRule = DCGRule(Compound(Atom("hello"), Atom("world")), Atom("something"))
      assert(dcgRule.generate == "hello(world) --> something.")
    }
  }

  "Infix operators" should {
    "generate compound terms" in {
      val compound = Atom("hello") ^: Atom("world")
      assert(compound.generate == "^(hello,world)")
    }

    "be chained to compose a nested compound term" in {
      val compound = Atom("hello") ^: Atom("world") ^: Atom("again")
      assert(compound.generate == "^(hello,^(world,again))")
    }
  }

  "Compound builder" should {
    import io.github.scalaquest.core.parsing.scalog.dsl.CompoundBuilder
    val hello = CompoundBuilder(Atom("hello"))

    "allow for the creation of a compound term" in {
      assert(hello(Atom("world")) == Compound(Atom("hello"), Atom("world")))
    }

    "be used with more than 1 argument" in {
      assert(
        hello(
          Atom("darkness"),
          Atom("my"),
          Atom("old"),
          Atom("friend")
        ) == Compound(
          Atom("hello"),
          Atom("darkness"),
          List(Atom("my"), Atom("old"), Atom("friend"))
        )
      )
    }
  }

  "Implicit operators" should {
    import io.github.scalaquest.core.parsing.scalog.dsl._
    val hello = CompoundBuilder(Atom("hello"))
    "allow the usage of strings as atoms" in {
      assert(hello("world") == Compound(Atom("hello"), Atom("world")))
    }

    "allow the usage of integers as numbers" in {
      assert(hello(0) == Compound(Atom("hello"), Number(0)))
    }

    "allow the usage of terms as facts" in {
      val dcgRule = hello("world") --> ListP("hello", "world")
      assert(
        dcgRule == DCGRule(
          Compound(Atom("hello"), Atom("world")),
          ListP(Atom("hello"), Atom("world"))
        )
      )
    }

    "allow the usage of infix operators on strings" in {
      assert(
        ("a" ^: "b" ^: "c") == Compound(
          Atom("^"),
          Atom("a"),
          List(Compound(Atom("^"), Atom("b"), List(Atom("c"))))
        )
      )
    }

    "allow the usage of infix operators on variables" in {
      val X    = Variable("X")
      val Y    = Variable("Y")
      val term = X ^: Y ^: hello(Y, X)

      assert(
        term == Compound(
          Atom("^"),
          Variable("X"),
          List(
            Compound(
              Atom("^"),
              Variable("Y"),
              List(Compound(Atom("hello"), Variable("Y"), List(Variable("X"))))
            )
          )
        )
      )
    }
  }

}
