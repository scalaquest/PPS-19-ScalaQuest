package io.github.scalaquest.core.model.behaviorBased.commons.pushing

import io.github.scalaquest.core.TestsUtils.model.Messages._
import io.github.scalaquest.core.TestsUtils._
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import org.scalatest.wordspec.AnyWordSpec

class CommonStringPusherTest extends AnyWordSpec {
  "A CommonStringPusher" should {
    val commonStringPusher = CommonStringPusher(SimpleModel)

    "handle Inspected messages" in {
      val inspected = Inspected(startRoom, Set(key, door), Map(Direction.North -> targetRoom))

      val result = commonStringPusher.push(Seq(inspected))
      val expResult = s"The ${startRoom.name} contains a ${door.name}, a ${key.name}.\n" +
        s"There is a ${targetRoom.name} in direction ${Direction.North.toString}."
      assert(result == expResult)
    }

    "handle Eaten messages" in {
      val eaten = Eaten(apple)
      assert(commonStringPusher.push(Seq(eaten)) == s"The ${apple.name} has been eaten!")
    }

    "handle Taken messages" in {
      val taken = Taken(key)
      assert(commonStringPusher.push(Seq(taken)) == s"The ${key.name} has been taken!")
    }

    "handle Opened messages" in {
      val opened = Opened(door)
      assert(commonStringPusher.push(Seq(opened)) == s"The ${door.name} has been opened!")
    }

    "handle Navigated messages" in {
      val navigated = Navigated(targetRoom)
      assert(commonStringPusher.push(Seq(navigated)) == s"You entered ${targetRoom.name}!")
    }
  }
}
