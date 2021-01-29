package io.github.scalaquest.core

import io.github.scalaquest.core.parsing.scalog.dsl.{CompoundBuilder, stringToAtom}

package object dictionary {

  val iv = CompoundBuilder("iv")
  val tv = CompoundBuilder("tv")
  val v  = CompoundBuilder("v")

  type Verb = BaseVerb with GenerateClause with GeneratePair

}
