package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel.{roomItemsLens, roomsLens}
import model.{CReactions, Reaction}

object Reactions {

  def killBasilisk: React =
    for {
      _ <- CReactions.modifyLocationItems(_ + Items.basiliskTooth.ref)
      s <- Reaction.messages(Pusher.BasiliskKilled)
    } yield s

  def moveBasiliskToChamber: React =
    s1 => {
      val chamberWithBasilisk =
        roomItemsLens.modify(_ + Items.basilisk.ref)(
          roomsLens.get(s1)(Geography.chamberOfSecrets.ref)
        )
      val x = for {
        _ <- CReactions.modifyLocationItems(_ - Items.basilisk.ref)
        _ <- CReactions.modifyLocationItems(_ - Items.stone.ref)
        _ <- Reaction(roomsLens.modify(_ + (Geography.chamberOfSecrets.ref -> chamberWithBasilisk)))
        _ <- CReactions.addDirectionToLocation(Direction.West, Geography.chamberOfSecrets)
        s <- Reaction.messages(Pusher.BasiliskMovedToChamber)
      } yield s
      x(s1)
    }

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
