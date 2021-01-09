package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.Actions.Take
import io.github.scalaquest.core.model.common.behaviors.StdCommonBehaviorsBase
import monocle.Lens

trait Takeable extends StdCommonBehaviorsBase {

  /**
   * The behavior of an Item that could be put into the bag.
   * @param onTakeExtra a Reaction to be possibly chained to the basic take Reaction.
   */
  case class Takeable(onTakeExtra: Option[Reaction] = None)(implicit
    bagLens: Lens[S, Set[I]],
    itemsLens: Lens[S, Map[Room, Set[I]]]
  ) extends CommonBehaviors.Takeable
    with ExtraUtils {

    override def triggers: Triggers = {
      // controlla se l'oggetto è nella room
      case (Take, item, None, state) if state.isInCurrentRoom(item) => take(item)
    }

    // The standard take reaction is to remove the item from the current room, and put it into the bag.
    // executes also onTakeExtra, if available
    private def take(item: I): Reaction =
      state => {
        // remove the item from the current room
        val currRoom = state.game.player.location
        val currRoomItemsUpd =
          itemsLens.get(state).get(currRoom).fold(Set[I]())(crItems => crItems + item)
        val stateWithoutItem = itemsLens.modify(_ + (currRoom -> currRoomItemsUpd))(state)

        // put the item into the bag
        val stateItemInBag = bagLens.modify(_ + item)(stateWithoutItem)

        // execute additional reaction, if specified
        applyExtraIfPresent(onTakeExtra)(stateItemInBag)
      }
  }
}
