package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.Actions.Take
import io.github.scalaquest.core.model.common.CommonBase
import io.github.scalaquest.core.model.common.behaviors.{CommonBehaviors, StdCommonBehaviors}
import monocle.Lens

/**
 * The trait makes possible to mix into the [[StdCommonBehaviors]] the standard implementation of
 * [[CommonBehaviors.CommonBehaviors.Takeable]].
 */
trait Takeable extends CommonBase {

  /**
   * Standard implementation of the [[CommonBehaviors.Takeable]].
   *
   * The behavior of an Item that could be put into the Bag of the player from the current room.
   * @param onTakeExtra
   *   Reaction to be executed into the State when taken, after the standard Reaction. It can be
   *   omitted.
   */
  case class Takeable(onTakeExtra: Option[Reaction] = None)(implicit
    bagLens: Lens[S, Set[I]],
    itemsLens: Lens[S, Map[Room, Set[I]]]
  ) extends CommonBehaviors.Takeable {

    override def triggers: Triggers = {
      // "Take the item"
      case (Take, item, None, state) if state.isInCurrentRoom(item) => take(item)
    }

    /**
     * Creates a Reaction that removes the item from the current room, put it into the bag, executes
     * the eventual extra reaction.
     * @param item
     *   The item to be taken.
     * @return
     *   A Reaction that removes the item from the current room, put it into the bag, executes the
     *   eventual extra reaction.
     */
    private def take(item: I): Reaction =
      state => {
        // remove the item from the current room
        val currRoom         = state.game.player.location
        val currRoomItemsUpd = itemsLens.get(state).get(currRoom).fold(Set[I]())(_ - item)
        val stateWithoutItem = itemsLens.modify(_ + (currRoom -> currRoomItemsUpd))(state)

        // put the item into the bag
        val stateItemInBag = bagLens.modify(_ + item)(stateWithoutItem)

        // execute additional reaction, if specified
        stateItemInBag.applyReactionIfPresent(onTakeExtra)
      }
  }
}
