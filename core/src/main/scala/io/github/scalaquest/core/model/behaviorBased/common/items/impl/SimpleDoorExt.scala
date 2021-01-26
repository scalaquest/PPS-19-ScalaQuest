package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.SimpleCommonItems
import io.github.scalaquest.core.model.behaviorBased.common.items.CommonItems

/**
 * The trait makes possible to mix into the [[SimpleCommonItems]] the standard implementation of
 * [[CommonItems.Door]].
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
