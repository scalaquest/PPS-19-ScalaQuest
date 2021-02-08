package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.Message
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonStringPusher

object Pusher {
  case object DeliciousMessage extends Message

  val defaultPusher: CommonStringPusher = CommonStringPusher(
    model,
    { case DeliciousMessage => "Delicious!" }
  )
}
