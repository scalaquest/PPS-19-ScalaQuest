/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.cli

import io.github.scalaquest.core.model.{Message, MessagePusher}

class TestStringPusher extends MessagePusher[String] {

  var messages: List[Message] = List()

  override def push(input: Seq[Message]): String = {
    messages = messages ++ input
    ""
  }
}
