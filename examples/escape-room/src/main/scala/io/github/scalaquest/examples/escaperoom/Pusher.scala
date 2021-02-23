package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CStringPusher
import io.github.scalaquest.core.model.{Message, StringPusher}
import model.CMessages._

object Pusher {

  // Example for a custom message
  case object DeliciousMessage extends Message

  val defaultPusher: StringPusher = CStringPusher(
    model,
    {
      case DeliciousMessage => "Delicious!"
      case Opened(Items.hatch) =>
        "The key slides into the lock easily. " +
          "With great effort, you open the hatch, and you see a dusty living room above you."
      case Opened(Items.chest) => "The chest swung open."

      case ReversedIntoLocation(items) if items.headOption contains Items.hatchKey =>
        s"There is an ${Items.hatchKey} inside it. Maybe it could be useful."

      case Won  => "Great! You are out of the house now and you win! Bye!"
      case Lost => "On no! The apple was poisoned, you died, game over. Bye!"
    }
  )
}
