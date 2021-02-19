package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.examples.pokequest.Items.snorlax
import model.{Update, locationRoomLens, roomItemsLens, Reaction, Reactions}

object CustomReactions {

  def wakeSnorlaxReaction: React =
    for {
      _ <- Reactions.addDirectionToLocation(Direction.North, Geography.forest)
      _ <- Reaction(Update((locationRoomLens composeLens roomItemsLens).modify(_ - snorlax.ref)))
      s <- Reaction.messages(Pusher.SnorlaxWoke)
    } yield s
}
