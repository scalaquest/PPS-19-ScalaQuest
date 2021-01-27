package io.github.scalaquest.examples

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.core.model.{Action, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel

package object escaperoom {
  import io.github.scalaquest.core.model.Action.Common._

  val myModel = SimpleModel

  type Model    = myModel.type
  type State    = myModel.S
  type Item     = myModel.I
  type Room     = myModel.RM
  type Reaction = myModel.Reaction

  var itemsSet: Set[Item] = Set(
    Items.redApple,
    Items.apple,
    Items.greenApple
  )

  // generated
  var items: Map[ItemRef, Item] = itemsSet.map(i => i.ref -> i).toMap

  // generated
  val actions: Map[String, Action] = Map(
    "take"    -> Take,
    "pick up" -> Take,
    "eat"     -> Eat,
    "open"    -> Open,
    "close"   -> Close
  )
}
