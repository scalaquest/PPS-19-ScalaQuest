package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.model.{Message, StringPusher}
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CStringPusher
import model.CMessages.Won

object Pusher {

  case object BasiliskKilled         extends Message
  case object BasiliskMovedToChamber extends Message
  case object BasiliskMovedToTunnel  extends Message
  case object KilledByBasilisk       extends Message
  case object KilledByTom            extends Message

  val defaultPusher: StringPusher = CStringPusher(
    model,
    {
      case BasiliskKilled => "You kill the basilisk thanks to the Gryffindor Sword."
      case BasiliskMovedToChamber =>
        "You throw the stone faraway and the terrible basilisk leaves the tunnel."
      case BasiliskMovedToTunnel => "The basilisk follow you in the tunnel."
      case KilledByBasilisk      => "The terrible basilisk has just killed you!"
      case KilledByTom           => "AVADA KEDAVRA!!!"
      case Won =>
        "You destroy the cursed diary. Tom is nearly related with the diary and he dies too. You save Ginny."
    }
  )
}
