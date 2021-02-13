package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.{Message, StringPusher}
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonStringPusher

object Pusher {
  import model._

  // Example for a custom message
  case object DeliciousMessage extends Message

  val defaultPusher: StringPusher = CommonStringPusher(
    model,
    {
      case DeliciousMessage => "Delicious!"
      case Opened(item) if item == Items.hatch =>
        "The key slides into the lock easily. " +
          "With great effort, you open the hatch, and you see a dusty living room above you."
      case Opened(item) if item == Items.coffer =>
        "The chest swung open. There is an old rusty key inside it. " +
          "Maybe it could be useful."
      case Won  => "Great! You are out of the house now and you win! Bye!"
      case Lost => "On no! The apple was poisoned, you died, game over. Bye!"
    }
  )
}
