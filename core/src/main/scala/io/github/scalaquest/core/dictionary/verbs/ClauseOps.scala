/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.dictionary.verbs

import io.github.scalaquest.core.parsing.scalog.Fact
import io.github.scalaquest.core.parsing.scalog.dsl.{intToNumber, stringToAtom, termToFact}

trait ClauseOps { self: BaseVerb =>

  protected def arity: Int

  def clause: Fact =
    prep match {
      case Some(value) => verb(arity, name, value)
      case None        => verb(arity, name)
    }
}
