package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Take
import io.github.scalaquest.core.model.{ItemRef, Room}
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.{
  CommonBehaviors,
  SimpleCommonBehaviors
}
import monocle.Lens

/**
 * The trait makes possible to mix into the [[SimpleCommonBehaviors]] the standard implementation of
 * [[CommonBehaviors.Takeable]].
 */
trait SimpleTakeableExt extends CommonBase {

  /**
   * Standard implementation of the Takeable.
   *
   * The behavior of an Item that could be put into the Bag of the player from the current room.
   * @param onTakeExtra
   *   Reaction to be executed into the State when taken, after the standard Reaction. It can be
   *   omitted.
   */
  case class SimpleTakeable(onTakeExtra: Option[Reaction] = None)(implicit
    playerBagLens: Lens[S, Set[ItemRef]],
    matchRoomsLens: Lens[S, Set[Room]],
    roomLens: Lens[Room, Set[ItemRef]]
  ) extends Takeable {

    override def triggers: ItemTriggers = {
      // "Take the item"
      case (Take, item, None, state) if state.isInCurrentRoom(item) => take(item)
    }

    /**
     * Returns a Reaction that removes the item from the current room, put it into the bag, executes
     * the eventual extra reaction.
     * @param item
     *   The item to be taken.
     * @return
     *   A Reaction that removes the item from the current room, put it into the bag, executes the
     *   eventual extra reaction.
     */
    private def take(item: I): Reaction =
      state => {

        val updCurrRoom = roomLens.modify(_ - item.id)(state.currentRoom)
        val takeItemFromRoom = Function.chain(
          Seq(matchRoomsLens.modify(_ + updCurrRoom), playerBagLens.modify(_ + item.id))
        )
        val stateWithItemInBag = takeItemFromRoom(state)

        // execute additional reaction, if specified
        stateWithItemInBag.applyReactionIfPresent(onTakeExtra)
      }
  }
}
