package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.SimpleCommonItems
import io.github.scalaquest.core.model.behaviorBased.common.items.CommonItems

/**
 * The trait makes possible to mix into the [[SimpleCommonItems]] the standard implementation of
 * [[CommonItems.Door]].
 */
trait SimpleDoor extends CommonBase {

  /**
   * Standard implementation of the common Door item.
   */
  case class SimpleDoor(
    itemRef: ItemRef,
    doorBehavior: RoomLink,
    additionalBehaviors: Behavior*
  ) extends Door {
    override def behaviors: Seq[Behavior] = doorBehavior +: additionalBehaviors

    override def isAccessible: Boolean = doorBehavior.isAccessible
  }
}
