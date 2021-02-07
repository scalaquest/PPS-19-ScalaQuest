package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.commons.CommonBase
import io.github.scalaquest.core.model.behaviorBased.commons.items.{
  CommonItemsExt,
  CommonSimpleItemsExt
}
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

/**
 * The trait makes possible to mix into the [[CommonSimpleItemsExt]] the standard implementation of
 * [[CommonItemsExt.Door]].
 */
trait SimpleDoorExt extends CommonBase {

  /**
   * Standard implementation of the common Door item.
   */
  case class SimpleDoor(
    description: ItemDescription,
    ref: ItemRef,
    doorBehavior: RoomLink,
    additionalBehaviors: ItemBehavior*
  ) extends Door {
    override def behaviors: Seq[ItemBehavior] = doorBehavior +: additionalBehaviors

    override def isAccessible: Boolean = doorBehavior.isAccessible
  }
}
