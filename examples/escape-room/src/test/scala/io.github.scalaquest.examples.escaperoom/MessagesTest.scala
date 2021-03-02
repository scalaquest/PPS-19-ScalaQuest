package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.examples.escaperoom.Messages.DeliciousMessage
import model.CMessages._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MessagesTest extends AnyWordSpec with Matchers {
  "The default pusher object" should {
    "handle the hatch opened message" in {
      Messages.pusher.push(
        Seq(Opened(Items.hatch))
      ) should not be Messages.pusher.notFound
    }

    "handle the doorway opened message" in {
      Messages.pusher.push(
        Seq(Opened(Items.doorway))
      ) should not be Messages.pusher.notFound
    }

    "handle the delicious message" in {
      Messages.pusher.push(Seq(DeliciousMessage)) should not be Messages.pusher.notFound
    }

    "handle the won message" in {
      Messages.pusher.push(Seq(Won)) should not be Messages.pusher.notFound
    }

    "handle the lost message" in {
      Messages.pusher.push(Seq(Lost)) should not be Messages.pusher.notFound
    }
  }
}
