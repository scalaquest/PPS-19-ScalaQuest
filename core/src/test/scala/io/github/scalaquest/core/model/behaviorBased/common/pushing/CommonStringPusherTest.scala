package io.github.scalaquest.core.model.behaviorBased.common.pushing

import io.github.scalaquest.core.TestsUtils.{apple, door, key, startRoom, targetRoom}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  Closed,
  Eaten,
  Inspected,
  Navigated,
  Opened,
  Taken
}
import org.scalatest.wordspec.AnyWordSpec

class CommonStringPusherTest extends AnyWordSpec {
  "A CommonStringPusher" should {
    val commonStringPusher = CommonStringPusher(SimpleModel)

    "handle Inspected messages" in {
      val inspected = Inspected(startRoom, Set(key, door))
      assert(
        commonStringPusher.push(
          inspected
        ) == s"The ${startRoom.name} contains a ${door.name}, a ${key.name}."
      )
    }

    "handle Eaten messages" in {
      val eaten = Eaten(apple)
      assert(commonStringPusher.push(eaten) == s"The ${apple.name} has been eaten!")
    }

    "handle Taken messages" in {
      val taken = Taken(key)
      assert(commonStringPusher.push(taken) == s"The ${key.name} has been taken!")
    }

    "handle Closed messages" in {
      val closed = Closed(door)
      assert(commonStringPusher.push(closed) == s"The ${door.name} has been closed!")
    }

    "handle Opened messages" in {
      val opened = Opened(door)
      assert(commonStringPusher.push(opened) == s"The ${door.name} has been opened!")
    }

    "handle Navigated messages" in {
      val navigated = Navigated(targetRoom)
      assert(commonStringPusher.push(navigated) == s"You entered ${targetRoom.name}!")
    }
  }
}
