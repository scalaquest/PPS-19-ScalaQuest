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
    def roomLink: RoomLink
  }

  /**
   * Standard implementation of the common [[Door]].
   */
  case class SimpleDoor(
    description: ItemDescription,
    ref: ItemRef,
    roomLinkBuilder: I => RoomLink,
    addBehaviorsBuilders: I => ItemBehavior*
  ) extends Door {
    override val roomLink: RoomLink           = roomLinkBuilder(this)
    override val behaviors: Seq[ItemBehavior] = roomLink +: addBehaviorsBuilders.map(_(this))
    override def isOpen: Boolean              = roomLink.isOpen
  }

  /**
   * Companion object for [[Door]]. Makes the initialization more succinct.
   */
  object Door {

    def apply(
      description: ItemDescription,
      roomLinkBuilder: I => RoomLink,
      addBehaviorsBuilders: Seq[I => ItemBehavior] = Seq.empty
    ): Door =
      SimpleDoor(description, ItemRef(description), roomLinkBuilder, addBehaviorsBuilders: _*)
  }
}
