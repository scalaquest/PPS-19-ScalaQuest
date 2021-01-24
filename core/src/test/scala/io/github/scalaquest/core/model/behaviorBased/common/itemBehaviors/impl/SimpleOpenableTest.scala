package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils.{simpleState, startRoom}
import io.github.scalaquest.core.model.Action.Common.Open
import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  Openable,
  SimpleGenericItem,
  SimpleKey,
  SimpleOpenable,
  SimpleState,
  geographyLens,
  playerBagLens
}
import io.github.scalaquest.core.pipeline.parser.ItemDescription
import org.scalatest.wordspec.AnyWordSpec

class SimpleOpenableTest extends AnyWordSpec {
  "An Openable behavior" when {

    val targetOpenable: SimpleState => Option[Openable] = state => {
      for {
        itemsInLoc <- state.matchState.geography.get(startRoom)
        openable <- itemsInLoc.collectFirst({ case SimpleGenericItem(_, _, openable: Openable) =>
          openable
        })
      } yield openable
    }

    "a key is required" when {
      val targetKey  = SimpleKey(ItemDescription("key"), new ItemRef {})
      val openable   = SimpleOpenable(requiredKey = Some(targetKey))
      val targetItem = SimpleGenericItem(ItemDescription("item"), new ItemRef {}, openable)

      val copyWKeyAndPortal = Function.chain(
        Seq(
          playerBagLens.modify(_ + targetKey),
          geographyLens.modify(_ + (startRoom -> Set(targetItem)))
        )
      )
      val stateWKeyAndItem: SimpleState = copyWKeyAndPortal(simpleState)

      "the user says 'open the item'" should {

        "let the item open only with the right Key" in {
          for {
            react <- targetItem.use(Open, stateWKeyAndItem, Some(targetKey)) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWKeyAndItem))
            openable <- targetOpenable(modState) toRight fail("Error into the test implementation")
          } yield assert(openable.isOpen, "The item is not in open state")
        }

        "not open without the right Key" in {
          assert(targetItem.use(Open, stateWKeyAndItem, None).isEmpty)
          assert(
            targetItem
              .use(Open, stateWKeyAndItem, Some(SimpleKey(ItemDescription("key"), new ItemRef {})))
              .isEmpty
          )
        }
      }
    }

    "a key is not required" when {
      val openable   = SimpleOpenable()
      val targetItem = SimpleGenericItem(ItemDescription("item"), new ItemRef {}, openable)
      val stateWPort: SimpleState =
        geographyLens.modify(_ + (startRoom -> Set(targetItem)))(simpleState)

      "the user says 'open the item'" should {
        "open without Key" in {
          for {
            react    <- targetItem.use(Open, stateWPort, None) toRight fail("Reaction not generated")
            modState <- Right(react(stateWPort))
            openable <- targetOpenable(modState) toRight fail("Error into the test implementation")
          } yield assert(openable.isOpen, "The item is not in open state")

        }
        "not open with any Key" in {
          assert(
            targetItem
              .use(Open, stateWPort, Some(SimpleKey(ItemDescription("key"), new ItemRef {})))
              .isEmpty
          )
        }
      }
    }
  }
}
