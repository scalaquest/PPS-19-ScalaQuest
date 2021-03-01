package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.model.Direction
import model.{CReactions, Reaction}

object Reactions {

  def killBasilisk: React =
    for {
      _ <- CReactions.modifyLocationItems(_ + Items.basiliskTooth.ref)
      s <- Reaction.messages(Pusher.BasiliskKilled)
    } yield s

  def moveBasiliskToChamber: React =
    for {
      _ <- CReactions.modifyLocationItems(_ - Items.basilisk.ref)
      _ <- CReactions.addDirectionToLocation(Direction.West, Geography.chamberOfSecrets)
      s <- Reaction.messages(Pusher.BasiliskMovedToChamber)
    } yield s

  def killedByBasilisk: React =
    for {
      _ <- Reaction.messages(Pusher.KilledByBasilisk)
      s <- CReactions.finishGame(false)
    } yield s

  def killedByTom: React =
    for {
      _ <- Reaction.messages(Pusher.KilledByTom)
      s <- CReactions.finishGame(false)
    } yield s

}
