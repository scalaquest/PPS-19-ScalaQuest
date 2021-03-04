package io.github.scalaquest.core.model

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.TriggerPusher.MessageTriggers
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TriggerPusherTest extends AnyWordSpec with Matchers {
  import TestsUtils.model.CMessages._

  "A TriggerPusher" when {
    "have some defined trigger" should {
      val pusher = new TriggerPusher[Int] {
        override def notFound: Int = -1

        override def combine(x: Int, y: Int): Int = x + y

        override def triggers: MessageTriggers[Int] = { case Print(_) =>
          1
        }
      }

      "print not found, it the message cannot be handled" in {
        pusher.push(Seq(new Message {})) shouldBe pusher.notFound
      }

      "find a match for the given sequence of messages, analyzing the triggers" in {
        pusher.push(Seq(Print("example"), Print("example"))) shouldBe 2
      }
    }

    "haven't any defined trigger" should {
      val pusher = new TriggerPusher[Int] {
        override def notFound: Int = -1

        override def combine(x: Int, y: Int): Int = x + y
      }
      "print not found, it the message cannot be handled" in {
        pusher.push(Seq(new Message {})) shouldBe pusher.notFound
      }
    }

  }

  "A StringPusher" should {
    val pusher = new StringPusher {
      override def triggers: MessageTriggers[String] = { case Print(msg) =>
        msg
      }
    }

    "return the empty message, if a match is not found" in {
      pusher.push(Seq(new Message {})) shouldBe pusher.notFound
    }

    "find a match for the given sequence of messages, analyzing the triggers" in {
      pusher.push(Seq(Print("hello"), Print("hello"))) shouldBe "hello\nhello"
    }
  }
}
