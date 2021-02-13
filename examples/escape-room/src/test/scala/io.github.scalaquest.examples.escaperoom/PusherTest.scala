package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.examples.escaperoom.Pusher.DeliciousMessage
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model._

class PusherTest extends AnyWordSpec with Matchers {
  "The default pusher object" should {
    "handle the hatch opened message" in {
      Pusher.defaultPusher.push(
        Seq(Opened(Items.hatch))
      ) should not be Pusher.defaultPusher.notFound
    }

    "handle the doorway opened message" in {
      Pusher.defaultPusher.push(
        Seq(Opened(Items.doorway))
      ) should not be Pusher.defaultPusher.notFound
    }

    "handle the delicious message" in {
      Pusher.defaultPusher.push(Seq(DeliciousMessage)) should not be Pusher.defaultPusher.notFound
    }

    "handle the won message" in {
      Pusher.defaultPusher.push(Seq(Won)) should not be Pusher.defaultPusher.notFound
    }

    "handle the lost message" in {
      Pusher.defaultPusher.push(Seq(Lost)) should not be Pusher.defaultPusher.notFound
    }
  }
}
