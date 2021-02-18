package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Eat
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils._
import TestsUtils.model._
import org.scalatest.matchers.should.Matchers

class EatableTest extends AnyWordSpec with Matchers {

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
            modState <- Right(react(stateItemInRoom)._1)
            msgs     <- Right(react(stateItemInRoom)._2)
          } yield {
            modState.location.items(modState) should not contain targetItem
            msgs should contain(Messages.Eaten(targetItem))
          }
        }

        "let the item disappear if it is in the bag" in {
          for {
            react <- targetItem.use(Eat, None)(stateItemInBag) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateItemInBag)._1)
            msgs     <- Right(react(stateItemInBag)._2)
          } yield {
            modState.bag should not contain targetItem
            msgs should contain(Messages.Eaten(targetItem))
          }
        }

        "not work if the item is not in the current room or into the bag" in {
          targetItem.use(Eat, None)(stateNoItemInRoom) shouldBe None
        }
      }
    }
  }
}
