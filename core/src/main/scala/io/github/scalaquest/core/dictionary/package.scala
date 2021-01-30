package io.github.scalaquest.core

import io.github.scalaquest.core.parsing.scalog.Clause
import io.github.scalaquest.core.parsing.scalog.dsl.{CompoundBuilder, stringToAtom}

package object dictionary {

  val verb = CompoundBuilder("verb").constructor

  type Verb  = BaseVerb with ClauseUtils with Meaning
  type VerbC = BaseVerb with ClauseUtils

  type VerbPrep = (String, Option[String])

  type Program = Set[Clause]

}
