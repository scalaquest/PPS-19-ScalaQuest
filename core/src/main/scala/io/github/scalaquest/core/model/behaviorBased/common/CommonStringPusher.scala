package io.github.scalaquest.core.model.behaviorBased.common

import io.github.scalaquest.core.model.MessagePusher.{MessageTriggers, StringMessageTriggers}
import io.github.scalaquest.core.model.behaviorBased.common.messages.CommonMessages
import io.github.scalaquest.core.model.ComposableStringPusher

// un pusher che funziona con i message common e risponde con delle robe preconfezionate
class CommonStringPusher(model: CommonMessages) extends ComposableStringPusher {

  override def additionalTriggers: MessageTriggers[String] = PartialFunction.empty

  final def commonTriggers: StringMessageTriggers = {
    case model.Eaten(item)     => s"The ${item.name} has been eaten!"
    case model.Taken(item)     => s"The ${item.name} has been taken!"
    case model.Closed(item)    => s"The ${item.name} has been closed!"
    case model.Opened(item)    => s"The ${item.name} has been opened!"
    case model.Navigated(room) => s"You entered ${room.name}!"
  }
}
