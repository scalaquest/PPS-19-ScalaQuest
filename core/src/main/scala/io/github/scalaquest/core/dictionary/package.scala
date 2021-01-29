package io.github.scalaquest.core

import io.github.scalaquest.core.parsing.scalog.dsl.{CompoundBuilder, stringToAtom}

package object dictionary {

  val verb = CompoundBuilder("verb").constructor

  type Verb = BaseVerb with ClauseUtils with Meaning

  type VerbPrep = (String, Option[String])

}
