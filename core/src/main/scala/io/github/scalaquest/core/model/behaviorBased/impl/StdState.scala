package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.{MatchState, ItemRef, Message, Model, Player, Room}

/**
 * This can be used as a mixin or as an extension for the model. Adds a simple implementation of the
 * State into the model.
 */
trait StdState extends Model {

  override type S = StdState

  case class StdState(matchState: StdMatchState, messages: Seq[Message]) extends State {

    override def extractRefs: Map[ItemRef, I] = {
      val allItems =
        matchState.hiddenItems ++ matchState.player.bag ++ matchState.geography.flatMap(_._2)
      allItems.foldLeft(Map.empty[ItemRef, I])((map, item) => map + (item.itemRef -> item))
    }
  }

  case class StdMatchState(
    player: StdPlayer,
    ended: Boolean,
    rooms: Set[Room],
    geography: Map[Room, Set[I]],
    hiddenItems: Set[I]
  ) extends MatchState[I]

  case class StdPlayer(bag: Set[I], location: Room) extends Player[I]
}
