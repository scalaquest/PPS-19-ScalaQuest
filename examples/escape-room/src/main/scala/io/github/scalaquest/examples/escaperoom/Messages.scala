package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.Message
import io.github.scalaquest.core.model.MessagePusher.MessageTriggers
import io.github.scalaquest.core.model.behaviorBased.common.CommonStringPusher

object Messages {
  case object SuperStonksPowered extends Message
  case class Print(msg: String)  extends Message

  case class TextualMessage(msg: String) extends Message

  val defaultPusher: CommonStringPusher = new CommonStringPusher(model) {

    override def additionalTriggers: MessageTriggers[String] = { case SuperStonksPowered =>
      "Became SuperStonks ( ͡° ͜ʖ ͡°)"
    }
  }
}
