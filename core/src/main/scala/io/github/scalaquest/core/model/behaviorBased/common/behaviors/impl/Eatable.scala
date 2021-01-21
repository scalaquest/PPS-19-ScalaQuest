package io.github.scalaquest.core.model.behaviorBased.common.behaviors.impl

import io.github.scalaquest.core.model.Action.Common.Eat
import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import monocle.Lens

/**
 * The trait makes possible to mix into StdCommonBehaviors the standard implementation of Eatable.
 */
trait Eatable extends CommonBase {

  /**
   * Standard implementation of the Eatable behavior.
   *
   * This is a behavior associated to an Item that can be eaten, if present in the bag or in the
   * room.
   * @param onEatExtra
   *   Reaction to be executed when the item has been successfully eaten, after the standard
   *   [[Reaction]]. It can be omitted.
   */
  case class Eatable(onEatExtra: Option[Reaction] = None)(implicit
    bagLens: Lens[S, Set[I]],
    itemsLens: Lens[S, Map[Room, Set[I]]]
  ) extends CommonBehaviors.Eatable {

    override def triggers: ItemTriggers = {
      // "Eat the item"
      case (Eat, item, None, state) if state.isInCurrentRoom(item) || state.isInBag(item) =>
        eat(item)
    }

    private def eat(item: I): Reaction =
      state => {

        // remove the item if it is in the current room
        val currRoom         = state.matchState.player.location
        val currRoomItemsUpd = itemsLens.get(state).get(currRoom).fold(Set[I]())(_ - item)
        val stateWithoutItem = itemsLens.modify(_ + (currRoom -> currRoomItemsUpd))(state)

        // remove the item if is into the bag
        val stateItemInBag = bagLens.modify(_ - item)(stateWithoutItem)

        // execute additional reaction, if specified
        stateItemInBag.applyReactionIfPresent(onEatExtra)
      }
  }
}
