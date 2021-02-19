package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonStringPusher
import io.github.scalaquest.core.model.{Message, StringPusher}

object Pusher {
  import model.Messages._

  // Example for a custom message
  case object DeliciousMessage extends Message

  val defaultPusher: StringPusher = CommonStringPusher(
    model,
    {
      case DeliciousMessage => "Delicious!"
      case Won              => "Great! You are out of the house now and you win! Bye!"
      case Lost             => "On no! The apple was poisoned, you died, game over. Bye!"
    }
  )
}
