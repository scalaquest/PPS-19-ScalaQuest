package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.Action.Common.Eat
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.wordspec.AnyWordSpec

class EatableTest extends AnyWordSpec {
  import TestsUtils._
  import TestsUtils.model._

  "A Eatable behavior" when {

    "applied to an item" when {
      val targetItem        = GenericItem(ItemDescription("item"), Seq(Eatable.builder()))
      val stateItemInRoom   = simpleState.copyWithItemInLocation(targetItem)
      val stateItemInBag    = simpleState.copyWithItemInBag(targetItem)
      val stateNoItemInRoom = itemsLens.modify(_ + (targetItem.ref -> targetItem))(simpleState)

      "the user says 'eat the item'" should {
        "let the item disappear if it is in the current room" in {
          for {
            react <- targetItem.use(Eat, None)(stateItemInRoom) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateItemInRoom))
          } yield assert(
            !modState.location.items(modState).contains(targetItem),
            "The item is into the room yet"
          )
        }

        "let the item disappear if it is in the bag" in {
          for {
            react <- targetItem.use(Eat, None)(stateItemInBag) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateItemInBag))
          } yield assert(
            !modState.bag.contains(targetItem),
            "The item is into the bag yet"
          )
        }

        "not work if the item is not in the current room or into the bag" in {
          assert(
            targetItem.use(Eat, None)(stateNoItemInRoom).isEmpty,
            "Generated a reaction when it shouldn't"
          )
        }
      }
    }
  }
}
