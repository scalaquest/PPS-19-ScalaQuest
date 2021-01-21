package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.CommonItemsImpl
import io.github.scalaquest.core.model.behaviorBased.common.items.CommonItems

/**
 * The trait makes possible to mix into the [[CommonItemsImpl]] the standard implementation of
 * [[CommonItems.CommonItems.Door]].
 */
trait Door extends CommonBase {

  /**
   * Standard implementation of the common Door item.
   */
  case class Door(
    itemRef: ItemRef,
    doorBehavior: CommonBehaviors.RoomLink,
    additionalBehaviors: Behavior*
  ) extends CommonItems.Door {
    override def behaviors: Seq[Behavior] = doorBehavior +: additionalBehaviors

    override def isAccessible: Boolean = doorBehavior.isAccessible
  }
}
