package io.github.scalaquest.core.model.behaviorBased.simple

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.{
  Go,
  Inspect,
  Open,
  Take
}
import io.github.scalaquest.core.model.{Direction, ItemDescription, ItemRef}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils.{model, _}
import TestsUtils.model._

class BehaviorBasedModelTest extends AnyWordSpec with Matchers {

  "A BehaviorBasedItem" when {
    "have some triggers" should {
      val behavior = new ItemBehavior {
        override def triggers: model.ItemTriggers = { case (Take, _, _) =>
          Reaction.empty
        }

        override def subject: model.I = apple
      }

      val item = new BehaviorBasedItem {
        override def behaviors: Seq[ItemBehavior] = Seq(behavior)

        override def description: ItemDescription = ItemDescription("item")

        override def ref: ItemRef = ItemRef(ItemDescription("item"))
      }

      val stateWithItem = simpleState.copyWithItemInLocation(item)

      "respond to use calls with a Reaction" in {
        item.use(Take, None)(stateWithItem).isDefined shouldBe true
      }

      "Respond with an empty value, if a match is not found" in {
        item.use(Open, None)(stateWithItem).isEmpty shouldBe true
      }

    }

    "not have any trigger" should {

      val item = new BehaviorBasedItem {

        override def description: ItemDescription = ItemDescription("item")

        override def ref: ItemRef = ItemRef(ItemDescription("item"))
      }

      val stateWithItem = simpleState.copyWithItemInLocation(item)

      "not respond to any calls" in {
        item.use(Open, None)(stateWithItem).isEmpty shouldBe true
      }
    }
  }

  "A BehaviorBasedGround" when {
    "have some behaviors" should {
      val ground = new BehaviorBasedGround {
        override def behaviors: Seq[GroundBehavior] = Seq(InspectableLocation())
      }

      "respond to use calls with a Reaction, if a match is found" in {
        ground.use(Inspect)(simpleState).isDefined shouldBe true
      }

      "respond with an empty value, if a match is not found" in {
        ground.use(Go(Direction.North))(simpleState).isEmpty shouldBe true
      }
    }
    "not have any behaviors" should {
      val ground = new BehaviorBasedGround {}

      "not respond to any calls" in {
        ground.use(Go(Direction.North))(simpleState).isEmpty shouldBe true
      }
    }
  }

  "An ItemBehavior" should {
    val itemBehavior = new ItemBehavior {
      override def subject: model.I = apple
    }

    "contain ItemTriggers as a PartialFunction" in {
      itemBehavior.triggers shouldBe PartialFunction.empty
    }
  }

  "A GroundBehavior" should {
    val itemBehavior = new GroundBehavior {}

    "contain a Seq of GroundTriggers" in {
      itemBehavior.triggers shouldBe PartialFunction.empty
    }
  }
}
