package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Eat
import io.github.scalaquest.core.model.{ItemRef, Message, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import monocle.Lens

/**
 * The trait makes possible to mix into StdCommonBehaviors the standard implementation of Eatable.
 */
trait SimpleEatableExt extends CommonBase {

  /**
   * Standard implementation of the Eatable behavior.
   *
   * This is a behavior associated to an Item that can be eaten, if present in the bag or in the
   * room.
   * @param onEatExtra
   *   Reaction to be executed when the item has been successfully eaten, after the standard
   *   [[Reaction]]. It can be omitted.
   */
  case class SimpleEatable(onEatExtra: Option[Reaction] = None)(implicit
    playerBagLens: Lens[S, Set[ItemRef]],
    matchRoomsLens: Lens[S, Map[RoomRef, RM]],
    roomLens: Lens[RM, Set[ItemRef]],
    messageLens: Lens[S, Seq[Message]]
  ) extends Eatable {

    override def triggers: ItemTriggers = {
      // "Eat the item"
      case (Eat, item, None, state) if state.isInScope(item) => eat(item)
    }

    def eat(item: I): Reaction =
      state => {
        val updCurrRoom = roomLens.modify(_ - item.ref)(state.currentRoom)
        state.applyReactions(
          matchRoomsLens.modify(_ + (updCurrRoom.ref -> updCurrRoom)),
          playerBagLens.modify(_ - item.ref),
          messageLens.modify(_ :+ Eaten(item)),
          onEatExtra.getOrElse(state => state)
        )
      }
  }
}
