package io.github.scalaquest.examples

import io.github.scalaquest.core.dictionary.VerbPrep
import io.github.scalaquest.core.model.{Action, Direction, ItemRef}
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
  val actions: Map[VerbPrep, Action] = Map(
    ("take", None)        -> Take,
    ("pick", Some("up"))  -> Take,
    ("eat", None)         -> Eat,
    ("open", None)        -> Open,
    ("close", None)       -> Close,
    ("go", Some("north")) -> Go(Direction.North)
  )
}
