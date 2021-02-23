package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.examples.pokequest.Items.snorlax
import model.{Reaction, CReactions, matchEndedLens}

object Reactions {
  var charizardWeaken = false

  def wakeSnorlax: React =
    for {
      _ <- CReactions.addDirectionToLocation(Direction.North, Geography.forest)
      _ <- CReactions.modifyLocationItems(_ - snorlax.ref)
      s <- Reaction.messages(Pusher.SnorlaxWoke)
    } yield s

  def attackCharizard: React =
    if (charizardWeaken) {
      for {
        _ <- Reaction(matchEndedLens.set(true))
        s <- Reaction.messages(Pusher.KilledCharizard)
      } yield s
    } else {
      charizardWeaken = true
      Reaction.messages(Pusher.WeakenCharizard)
    }

  def catchCharizard: React =
    if (charizardWeaken) {
      CReactions.finishGame(true)

    } else {
      for {
        _ <- Reaction(matchEndedLens.set(true))
        s <- Reaction.messages(Pusher.FailedToCaptureCharizard)
      } yield s
    }
}
