package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.examples.escaperoom.Pusher.DeliciousMessage
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model._
import model.Messages._

class PusherTest extends AnyWordSpec with Matchers {
  "The default pusher object" should {
    "handle the hatch opened message" in {
      assert(Pusher.defaultPusher.push(Opened(Items.hatch)) !== "")
    }

    "handle the doorway opened message" in {
      assert(Pusher.defaultPusher.push(Opened(Items.doorway)) !== "")
    }

    "handle the delicious message" in {
      assert(Pusher.defaultPusher.push(DeliciousMessage) !== "")
    }

    "handle the won message" in {
      assert(Pusher.defaultPusher.push(Won) !== "")
    }

    "handle the lost message" in {
      assert(Pusher.defaultPusher.push(Lost) !== "")
    }
  }
}
