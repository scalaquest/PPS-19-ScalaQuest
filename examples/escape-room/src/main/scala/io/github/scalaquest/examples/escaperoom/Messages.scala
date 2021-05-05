/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CStringPusher
import io.github.scalaquest.core.model.{Message, StringPusher}
import model.CMessages._

/**
 * Custom messages required by the example, and the pusher to handle them.
 */
object Messages {

  // Example for a custom message
  case object DeliciousMessage extends Message

  val pusher: StringPusher = CStringPusher(
    model,
    {
      case DeliciousMessage => "Delicious!"
      case Opened(Items.hatch) =>
        "The key slides into the lock easily. " +
          "With great effort, you open the hatch, and you see a dusty living room above you."

      case Opened(Items.chest) => "The chest swung open."

      case ReversedIntoLocation(items) if items.headOption contains Items.hatchKey =>
        s"There is an ${Items.hatchKey} inside it. Maybe it could be useful."

      case Won => "Great! You are out of the house now and you win! Bye!"

      case Lost => "On no! The apple was poisoned, you died, game over. Bye!"
    }
  )
}
