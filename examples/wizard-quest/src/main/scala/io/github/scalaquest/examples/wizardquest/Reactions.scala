package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.model.Direction
import model.{CReactions, Reaction}

object Reactions {

  var isInitState = true
  var swordShown  = false

  def showTheSword: React = {
    swordShown = true

    for {
      _ <- CReactions.modifyLocationItems(_ + Items.gryffindorSword.ref)
      s <- CReactions.addMessage(Pusher.SwordShown)
    } yield s
  }

  def killTheBasilisk: React =
    for {
      _ <- CReactions.modifyLocationItems(_ + Items.basiliskTooth.ref)
      _ <- CReactions.modifyLocationItems(_ - Items.basilisk.ref)
      s <- Reaction.messages(Pusher.BasiliskKilled, Pusher.ToothSpawned)
    } yield s

  def basiliskMovesBack: React =
    for {
      _ <- CReactions.modifyLocationItems(_ -- Set(Items.basilisk.ref, Items.stone.ref))
      _ <- CReactions.modifyRoomItems(Geography.chamberOfSecrets.ref, _ + Items.basilisk.ref)
      _ <- CReactions.addDirectionToLocation(Direction.West, Geography.chamberOfSecrets)
      s <- CReactions.addMessage(Pusher.BasiliskMovedToChamber)
    } yield s

  def getKilledByBasilisk: React =
    for {
      _ <- CReactions.addMessage(Pusher.KilledByBasilisk)
      s <- CReactions.finishGame(false)
    } yield s

  def getKilledByTom: React =
    for {
      _ <- CReactions.addMessage(Pusher.KilledByTom)
      s <- CReactions.finishGame(false)
    } yield s

}
