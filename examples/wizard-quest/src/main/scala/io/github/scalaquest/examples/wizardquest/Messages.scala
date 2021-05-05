/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.model.{Message, StringPusher}
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CStringPusher
import model.CMessages

/**
 * Custom messages required by the example, and the pusher to handle them.
 */
object Messages {
  case object BasiliskKilled         extends Message
  case object ToothSpawned           extends Message
  case object BasiliskMovedToChamber extends Message
  case object EscapingFromBasilisk   extends Message
  case object KilledByBasilisk       extends Message
  case object KilledByTom            extends Message
  case object SwordShown             extends Message

  val pusher: StringPusher = CStringPusher(
    model,
    {
      case Messages.BasiliskKilled =>
        "Ffffkrrrrshhzzzwooooom..woom..woooom..! Shiiiiing! Splush! The battle was hard, " +
          "but with great effort you killed the basilisk. The Gryffindor Sword is a " +
          "portent! Tom widens his eyes at this sight, but does not give up."

      case Messages.ToothSpawned =>
        "Following the battle, its tooth stuck in your arm. You detach it, and you throw " +
          "it to the ground."

      case Messages.BasiliskMovedToChamber =>
        "You throw the stone faraway. Hearing the sound, the basilisk leaves the tunnel, " +
          "and follow it. You notice something strange into the sorting hat. Maybe it is " +
          "worth to take a look."

      case Messages.EscapingFromBasilisk =>
        "You keep running at full speed towards the tunnel, trying to leave behind the " +
          "basilisk. Once you enter, you realize that the tunnel has no way out. The " +
          "basilisk entered the tunnel also, and moves slowly towards you. You cannnot " +
          "go back without distract it. Think quickly! Maybe something on the ground " +
          "could help you."

      case Messages.SwordShown =>
        "Hey! The Gryffindor Sword spawn fron the hat! Fancy! This is exactly what you " +
          "needed!"

      case Messages.KilledByBasilisk =>
        "Oh no! The terrible Basilisk just killed you following this move! Try again " +
          "next time."

      case Messages.KilledByTom => "AVADA KEDAVRA!!! Tom killed you. Bye!"

      case CMessages.Won =>
        "Krrr! Splush! You destroyed the cursed diary. Tom is nearly related with the " +
          "diary and he dies too. Horray! You take Ginny and you bring her in a safe place."
    }
  )
}
