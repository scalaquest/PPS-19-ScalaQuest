package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.examples.pokequest.Items.snorlax
import model.{Reaction, Reactions, Update, locationRoomLens, roomItemsLens, matchEndedLens}

object CustomReactions {
  var charizardWeaken = false

  def wakeSnorlax: React =
    for {
      _ <- Reactions.addDirectionToLocation(Direction.North, Geography.forest)
      _ <- Reactions.modifyLocationItems(_ - snorlax.ref)
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
      Reactions.finishGame(true)

    } else {
      for {
        _ <- Reaction(matchEndedLens.set(true))
        s <- Reaction.messages(Pusher.FailedToCaptureCharizard)
      } yield s
    }
}
