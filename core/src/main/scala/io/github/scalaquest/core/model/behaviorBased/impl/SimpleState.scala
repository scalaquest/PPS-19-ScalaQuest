package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.{MatchState, ItemRef, Message, Model, Player, Room}

/**
 * This can be used as a mixin or as an extension for the model. Adds a simple implementation of the
 * State into the model.
 */
trait SimpleState extends Model {

  override type S = SimpleState

  case class SimpleState(matchState: SimpleMatchState, messages: Seq[Message]) extends State {

    override def extractRefs: Map[ItemRef, I] = {
      val allItems =
        matchState.hiddenItems ++ matchState.player.bag ++ matchState.geography.flatMap(_._2)
      allItems.foldLeft(Map.empty[ItemRef, I])((map, item) => map + (item.itemRef -> item))
    }
  }

  case class SimpleMatchState(
    player: SimplePlayer,
    ended: Boolean,
    geography: Map[Room, Set[I]],
    hiddenItems: Set[I]
  ) extends MatchState[I]

  case class SimplePlayer(bag: Set[I], location: Room) extends Player[I]
}
