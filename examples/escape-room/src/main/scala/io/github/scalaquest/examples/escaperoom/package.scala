package io.github.scalaquest.examples

import io.github.scalaquest.core.model.Action

package object escaperoom {
  import io.github.scalaquest.core.model.Action.Common._

  val actions: Map[String, Action] = Map(
    "take"    -> Take,
    "pick up" -> Take,
    "eat"     -> Eat,
    "open"    -> Open,
    "close"   -> Close
  )
}
