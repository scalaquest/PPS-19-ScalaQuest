package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.examples.pokequest.Items.snorlax
import model.{Reaction, CReactions, matchEndedLens}

/**
 * Custom reactions required by the example.
 */
object Reactions {
  var charizardWeaken = false

  def wakeSnorlax: React =
    for {
      _ <- CReactions.addDirectionToLocation(Direction.North, Geography.forest)
      _ <- CReactions.modifyLocationItems(_ - snorlax.ref)
      s <- CReactions.addMessage(Messages.SnorlaxWoke)
    } yield s

  def attackCharizard: React =
    if (charizardWeaken) {
      for {
        _ <- Reaction(matchEndedLens.set(true))
        s <- CReactions.addMessage(Messages.KilledCharizard)
      } yield s
    } else {
      charizardWeaken = true
      CReactions.addMessage(Messages.WeakenCharizard)
    }

  def catchCharizard: React =
    if (charizardWeaken) CReactions.finishGame(true)
    else
      for {
        _ <- Reaction(matchEndedLens.set(true))
        s <- CReactions.addMessage(Messages.FailedToCaptureCharizard)
      } yield s
}
