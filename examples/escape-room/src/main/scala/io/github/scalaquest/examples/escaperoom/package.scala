package io.github.scalaquest.examples

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel

package object escaperoom {
  import io.github.scalaquest.core.model.Action.Common._

  val myModel: SimpleModel.type = SimpleModel

  type Model    = myModel.type
  type State    = myModel.S
  type Item     = myModel.I
  type Room     = myModel.RM
  type Reaction = myModel.Reaction

  val actions: Map[String, Action] = Map(
    "take"    -> Take,
    "pick up" -> Take,
    "eat"     -> Eat,
    "open"    -> Open,
    "close"   -> Close
  )
}
