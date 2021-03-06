package io.github.scalaquest.core.model.behaviorBased.commons.pushing

import io.github.scalaquest.core.TestsUtils.{apple, door, startRoom, targetRoom}
import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.TestsUtils.model.CMessages._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CStringPusherTest extends AnyWordSpec with Matchers {
  "A CommonStringPusher" when {
    "passed to him the SimpleModel" should {
      val commonStringPusher = CStringPusher(SimpleModel)

      "handle Inspected messages" should {
        "not return any rooms if there aren't neighbors" in {
          val inspected =
            Inspected(startRoom, Set(TestsUtils.key, door), Map.empty)
          commonStringPusher.push(Seq(inspected)) should include("cannot go")
        }
        "return neighbors if presents" in {
          val inspected =
            Inspected(startRoom, Set(TestsUtils.key, door), Map(Direction.North -> targetRoom))
          commonStringPusher.push(Seq(inspected)) should not be commonStringPusher.notFound
        }
      }

      "handle InspectedBag messages" in {
        val inspectedBag =
          InspectedBag(Set(apple, TestsUtils.key))
        commonStringPusher.push(Seq(inspectedBag)) should not be commonStringPusher.notFound
      }

      "handle Eaten messages" in {
        val eaten = Eaten(apple)
        commonStringPusher.push(Seq(eaten)) should not be commonStringPusher.notFound
      }

      "handle Taken messages" in {
        val taken = Taken(apple)
        commonStringPusher.push(Seq(taken)) should not be commonStringPusher.notFound
      }

      "handle Opened messages" in {
        val opened = Opened(door)
        commonStringPusher.push(Seq(opened)) should not be commonStringPusher.notFound
      }

      "handle AlreadyOpened messages" in {
        val alreadyOpened = AlreadyOpened(door)
        commonStringPusher.push(Seq(alreadyOpened)) should not be commonStringPusher.notFound
      }

      "handle FailedToOpened messages" in {
        val failedToOpen = FailedToOpen(door)
        commonStringPusher.push(Seq(failedToOpen)) should not be commonStringPusher.notFound
      }

      "handle Navigated messages" in {
        val navigated = Navigated(targetRoom)
        commonStringPusher.push(Seq(navigated)) should not be commonStringPusher.notFound
      }

      "handle Welcome messages" in {
        val welcome = Welcome("hello?")
        commonStringPusher.push(Seq(welcome)) should not be commonStringPusher.notFound
      }

      "handle Won messages" in {
        commonStringPusher.push(Seq(Won)) should not be commonStringPusher.notFound
      }

      "handle Lost messages" in {
        commonStringPusher.push(Seq(Lost)) should not be commonStringPusher.notFound
      }

      "handle FailedToEnter messages" in {
        val failedToEnter = FailedToEnter(door)
        commonStringPusher.push(Seq(failedToEnter)) should not be commonStringPusher.notFound
      }

      "handle ReversedIntoLocation" in {
        val reversedIntoLocation = ReversedIntoLocation(Set(apple, TestsUtils.key))
        commonStringPusher.push(Seq(reversedIntoLocation)) should not be commonStringPusher.notFound
      }
    }
  }
}
