package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.MessagePusher.MessageTriggers
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.TestsUtils

class MessagePusherTest extends AnyWordSpec with Matchers {
  import TestsUtils.model._

  "A MessagePusher" should {
    val pusher = new MessagePusher[Int] {
      override def notFound: Int                  = -1
      override def push(input: Seq[Message]): Int = notFound
    }

    "have some defined Message triggers" in {
      pusher.triggers shouldBe PartialFunction.empty
    }

    "find a match for the given message, analyzing the triggers" in {
      pusher.push(new Message {}) shouldBe -1
    }

    "print not found, it the message cannot be handled" in {
      pusher.push(new Message {}) shouldBe pusher.notFound
    }

    "find a match for the given sequence of messages, analyzing the triggers" in {
      pusher.push(Seq(new Message {}, new Message {})) shouldBe -1
    }
  }

  "A StringPusher" should {
    val pusher = new StringPusher {
      override def triggers: MessageTriggers[String] = { case Printed(msg) => msg }
    }

    "return the empty message, if a match is not found" in {
      pusher.push(new Message {}) shouldBe pusher.notFound
    }

    "find a match for the given message, analyzing the triggers" in {
      pusher.push(Printed("hello")) shouldBe "hello"
    }

    "find a match for the given sequence of messages, analyzing the triggers" in {
      pusher.push(Seq(Printed("hello"), Printed("hello"))) shouldBe "hello\nhello"
    }
  }
}
