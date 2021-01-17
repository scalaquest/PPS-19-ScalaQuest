package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.Actions.Eat
import io.github.scalaquest.core.model.common.behaviors.StdCommonBehaviorsBase
import monocle.Lens

trait Eatable extends StdCommonBehaviorsBase {

  /**
   * Standard implementation of the [[CommonBehaviors.Eatable]]
   *
   * The behavior that the item could be eaten if is present in the bag or in the room
   * @param onEatExtra
   *   extra [[Reaction]] that happen if the item is eaten.
   */
  case class Eatable(onEatExtra: Option[Reaction] = None)(implicit
    bagLens: Lens[S, Set[I]],
    itemsLens: Lens[S, Map[Room, Set[I]]]
  ) extends CommonBehaviors.Eatable {

    override def triggers: Triggers = {
      // "Eat the item"
      case (Eat, item, None, state) if state.isInCurrentRoom(item) || state.isInBag(item) =>
        eat(item)
    }

    private def eat(item: I): Reaction =
      state => {

        // remove the item if is in the current room
        val currRoom         = state.game.player.location
        val currRoomItemsUpd = itemsLens.get(state).get(currRoom).fold(Set[I]())(_ - item)
        val stateWithoutItem = itemsLens.modify(_ + (currRoom -> currRoomItemsUpd))(state)

        // remove the item if is into the bag
        val stateItemInBag = bagLens.modify(_ - item)(stateWithoutItem)

        // execute additional reaction, if specified
        stateItemInBag.applyReactionIfPresent(onEatExtra)
      }
  }
}
