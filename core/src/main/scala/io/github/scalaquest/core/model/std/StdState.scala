package io.github.scalaquest.core.model.std

import io.github.scalaquest.core.model.{Action, GameState, ItemRef, Message, Model, Player, Room}
import io.github.scalaquest.core.pipeline.parser.ItemDescription

/**
 * This can be used as a mixin or as an extension for the model. Adds a simple implementation of the
 * State into the model.
 */
trait StdState extends Model {

  override type S = StdState

  case class StdState(actions: Map[String, Action], game: StdGameState, messages: Seq[Message]) extends State {

    override def extractRefs: Map[ItemRef, I] = {
      val allItems = game.hiddenItems ++ game.player.bag ++ game.itemsInRooms.flatMap(_._2)
      allItems.foldLeft(Map.empty[ItemRef, I])((map, item) => map + (item.itemRef -> item))
    }
  }

  case class StdGameState(
    player: StdPlayer,
    ended: Boolean,
    rooms: Set[Room],
    itemsInRooms: Map[Room, Set[I]],
    hiddenItems: Set[I]
  ) extends GameState[I]

  case class StdPlayer(bag: Set[I], location: Room) extends Player[I]
}
