package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.Action.Common.Open
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import org.scalatest.wordspec.AnyWordSpec

class OpenableTest extends AnyWordSpec {
  import TestsUtils._
  import TestsUtils.model._

  "An Openable behavior" when {

    def targetOpenable: SimpleState => Option[Openable] =
      state => {
        state.location
          .items(state)
          .map(_.behaviors.headOption)
          .collect({ case Some(beh) => beh })
          .collectFirst({ case o: Openable => o })
      }

    "a key is required" when {
      val targetKey  = Key(ItemDescription("key"))
      val openable   = Openable.builder(requiredKey = Some(targetKey))
      val targetItem = GenericItem(ItemDescription("item"), Seq(openable))

      val stateWithTarget    = simpleState.copyWithItemInLocation(targetItem)
      val stateWKeyAndTarget = stateWithTarget.copyWithItemInBag(targetKey)

      "the user says 'open the item'" should {

        "let the item open only with the right Key" in {
          for {
            react <- targetItem.use(Open, Some(targetKey))(stateWKeyAndTarget) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWKeyAndTarget))
            openable <- targetOpenable(modState) toRight fail("Error into the test implementation")
          } yield assert(openable.isOpen, "The item is not in open state")
        }

        "not open without the right Key" in {
          val wrongKey = Key(ItemDescription("wrongkey"))
          assert(targetItem.use(Open, None)(stateWKeyAndTarget).isEmpty)
          assert(targetItem.use(Open, Some(wrongKey))(stateWKeyAndTarget).isEmpty)
        }
      }
    }

    "a key is not required" when {
      val openable              = Openable.builder()
      val itemDescription       = ItemDescription("item")
      val targetItem            = SimpleGenericItem(itemDescription, ItemRef(itemDescription), openable)
      val stateWithTargetInRoom = simpleState.copyWithItemInLocation(targetItem)

      "the user says 'open the item'" should {
        "open without Key" in {
          for {
            react <- targetItem.use(Open, None)(stateWithTargetInRoom) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWithTargetInRoom))
            openable <- targetOpenable(modState) toRight fail("Error into the test implementation")
          } yield assert(openable.isOpen, "The item is not in open state")

        }
        "not open with any Key" in {
          val wrongKey = Key(ItemDescription("wrongkey"))
          assert(targetItem.use(Open, Some(wrongKey))(stateWithTargetInRoom).isEmpty)
        }
      }
    }
  }
}
