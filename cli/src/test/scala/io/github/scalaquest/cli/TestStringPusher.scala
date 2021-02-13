package io.github.scalaquest.cli

import io.github.scalaquest.core.model.{Message, MessagePusher}

class TestStringPusher extends MessagePusher[String] {

  var messages: List[Message] = List()

  override def push(input: Seq[Message]): String = {
    messages = messages ++ input
    ""
  }
}
