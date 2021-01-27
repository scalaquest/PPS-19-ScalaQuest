package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Take
import io.github.scalaquest.core.model.{ItemRef, Message, RoomRef}
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
    matchRoomsLens: Lens[S, Map[RoomRef, RM]],
    roomLens: Lens[RM, Set[ItemRef]],
    messageLens: Lens[S, Seq[Message]]
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
    def take(item: I): Reaction =
      state => {
        val updCurrRoom = roomLens.modify(_ - item.ref)(state.currentRoom)
        state.applyReactions(
          matchRoomsLens.modify(_ + (updCurrRoom.ref -> updCurrRoom)),
          playerBagLens.modify(_ + item.ref),
          messageLens.modify(_ :+ Taken(item)),
          onTakeExtra.getOrElse(state => state)
        )
      }
  }
}
