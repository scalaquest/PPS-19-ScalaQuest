package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.RoomLinkExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the Door Item.
 */
trait DoorExt extends BehaviorBasedModel with StateUtilsExt with RoomLinkExt {

  /**
   * A [[BehaviorBasedItem]] that should work as a link between two different [[Room]] s.
   */
  trait Door extends BehaviorBasedItem {
    def isOpen: Boolean
  }

  /**
   * Standard implementation of the common [[Door]].
   */
  case class SimpleDoor(
    description: ItemDescription,
    ref: ItemRef,
    doorBehavior: RoomLink,
    additionalBehaviors: ItemBehavior*
  ) extends Door {
    override def behaviors: Seq[ItemBehavior] = doorBehavior +: additionalBehaviors
    override def isOpen: Boolean              = doorBehavior.isOpen
  }

  /**
   * Companion object for [[Door]]. Makes the initialization more succinct.
   */
  object Door {

    def apply(
      description: ItemDescription,
      doorBehavior: RoomLink,
      additionalBehaviors: ItemBehavior*
    ): Door = SimpleDoor(description, ItemRef(description), doorBehavior, additionalBehaviors: _*)
  }
}
