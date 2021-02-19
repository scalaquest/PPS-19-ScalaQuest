package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonStringPusher
import io.github.scalaquest.core.model.{Message, StringPusher}

object Pusher {
  import model.Messages._

  // Example for a custom message
  case object SnorlaxWoke   extends Message
  case object FreePlayFlute extends Message

  val defaultPusher: StringPusher = CommonStringPusher(
    model,
    {
      case FreePlayFlute => "What a sweet melody!"
      case SnorlaxWoke =>
        "The sweet melody of the pokéflute woke up the Snorlax, that moved away! Now you can " +
          "proceed to the North."
      case Won => "Great! You are out of the house now and you win! Bye!"
      case Lost =>
        "On no! Snorlax attacked you, and you died. Try to do something else, next time. Bye!"
    }
  )
}
