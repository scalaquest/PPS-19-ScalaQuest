package io.github.scalaquest.core.dictionary.verbs

import io.github.scalaquest.core.parsing.scalog.Fact
import io.github.scalaquest.core.parsing.scalog.dsl.{termToFact, intToNumber, stringToAtom}

trait ClauseOps { self: BaseVerb =>

  def arity: Int

  def clause: Fact =
    prep match {
      case Some(value) => verb(arity, name, value)
      case None        => verb(arity, name)
    }
}
