package io.github.scalaquest.examples.hauntedhouse

import io.github.scalaquest.core.model.Message
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonStringPusher

object Messages {
  case object SuperStonksPowered extends Message
  case class Print(msg: String)  extends Message

  case class TextualMessage(msg: String) extends Message

  val defaultPusher: CommonStringPusher = CommonStringPusher(
    model,
    {
      case SuperStonksPowered => "I'm Superstonks now!"
      case Print(msg)         => msg
    }
  )

}
