package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.parsing.scalog.dsl._
import io.github.scalaquest.core.parsing.scalog.{Atom, Clause, Fact, Term, Variable}

/*
trait GenerateClause {
  def clause: Clause
}

 */

trait ClauseUtils { self: BaseVerb =>

  def arity: Int

  def clause: Fact =
    prep match {
      case Some(value) => verb(arity, name, value)
      case None        => verb(arity, name)
    }
}
