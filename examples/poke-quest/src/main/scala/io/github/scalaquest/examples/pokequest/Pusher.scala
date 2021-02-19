package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonStringPusher
import io.github.scalaquest.core.model.{Message, StringPusher}

object Pusher {
  import model.Messages._

  // Example for a custom message
  case object SnorlaxWoke              extends Message
  case object FreePlayFlute            extends Message
  case object WeakenCharizard          extends Message
  case object KilledCharizard          extends Message
  case object FailedToCaptureCharizard extends Message

  val defaultPusher: StringPusher = CommonStringPusher(
    model,
    {
      case FreePlayFlute => "What a sweet melody!"
      case SnorlaxWoke =>
        "The sweet melody of the pokéflute woke up the Snorlax, that moved away! Now you can " +
          "proceed to the North."
      case Won => "Great! catch Charizard, you win! Bye!"
      case Lost =>
        "On no! Snorlax attacked you, and you died. Try to do something else, next time. Bye!"
      case KilledCharizard => "Oh no! You killed Charizard, you lost. Bye!"
      case WeakenCharizard => "It's super-effective! Charizard is now really weak."
      case FailedToCaptureCharizard =>
        "Oh no! You tried to catch Charizard, but it was too strong yet and flied away. " +
          "You lost. Bye!"
      case Navigated(room) if room == Geography.forest =>
        "You entered the forest, and you see a Charizard into " +
          "the grass! Our battle will be epic!"
    }
  )
}
