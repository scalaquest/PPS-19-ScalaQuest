package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.model.common.Actions.Open
import io.github.scalaquest.core.model.std.StdModel.{
  GenericItem,
  Key,
  Openable,
  StdState,
  bagLens,
  itemsLens
}
import org.scalatest.wordspec.AnyWordSpec

class OpenableTest extends AnyWordSpec {
  "An Openable behavior" when {
    val simpleState: StdState = BehaviorsTestsUtils.simpleState

    val targetOpenable: StdState => Option[Openable] = state => {
      for {
        itemsInLoc <- state.game.itemsInRooms.get(BehaviorsTestsUtils.startRoom)
        openable   <- itemsInLoc.collectFirst({ case GenericItem(_, openable: Openable) => openable })
      } yield openable
    }

    "a key is required" when {
      val targetKey  = Key("targetKey")
      val openable   = Openable(requiredKey = Some(targetKey))
      val targetItem = GenericItem("openable", openable)

      val copyWKeyAndPortal = Function.chain(
        Seq(
          bagLens.modify(_ + targetKey),
          itemsLens.modify(_ + (BehaviorsTestsUtils.startRoom -> Set(targetItem)))
        )
      )
      val stateWKeyAndItem: StdState = copyWKeyAndPortal(simpleState)

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
          assert(targetItem.use(Open, stateWKeyAndItem, Some(Key("Wrong key"))).isEmpty)
        }
      }
    }

    "a key is not required" when {
      val openable   = Openable()
      val targetItem = GenericItem("openable", openable)
      val stateWPort: StdState =
        itemsLens.modify(_ + (BehaviorsTestsUtils.startRoom -> Set(targetItem)))(simpleState)

      "the user says 'open the item'" should {
        "open without Key" in {
          for {
            react    <- targetItem.use(Open, stateWPort, None) toRight fail("Reaction not generated")
            modState <- Right(react(stateWPort))
            openable <- targetOpenable(modState) toRight fail("Error into the test implementation")
          } yield assert(openable.isOpen, "The item is not in open state")

        }
        "not open with any Key" in {
          assert(targetItem.use(Open, stateWPort, Some(Key("A key"))).isEmpty)
        }
      }
    }
  }
}
