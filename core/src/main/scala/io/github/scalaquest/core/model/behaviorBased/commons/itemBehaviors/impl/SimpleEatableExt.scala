package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Eat
import io.github.scalaquest.core.model.{ItemRef, Message, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.commons.CommonBase
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
  case class SimpleEatable(onEatExtra: Option[Reaction] = None) extends Eatable {

    override def triggers: ItemTriggers = {
      // "Eat the item"
      case (Eat, item, None, state) if state.isInScope(item) => eat(item)
    }

    def eat(item: I): Reaction =
      state => {
        // todo locationItemsLens??
        val updLocation = roomItemsLens.modify(_ - item.ref)(state.location)

        state.applyReactions(
          roomsLens.modify(_ + (updLocation.ref -> updLocation)),
          bagLens.modify(_ - item.ref),
          messageLens.modify(_ :+ Eaten(item)),
          onEatExtra.getOrElse(state => state)
        )
      }
  }
}
