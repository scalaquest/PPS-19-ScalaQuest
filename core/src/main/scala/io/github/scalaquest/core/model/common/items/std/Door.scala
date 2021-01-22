package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.common.CommonBase
import io.github.scalaquest.core.model.common.items.StdCommonItems
import io.github.scalaquest.core.model.common.items.CommonItems
import io.github.scalaquest.core.pipeline.parser.ItemDescription

/**
 * The trait makes possible to mix into the [[StdCommonItems]] the standard implementation of
 * [[CommonItems.CommonItems.Door]].
 */
trait Door extends CommonBase {

  /**
   * Standard implementation of the common Door item.
   */
  case class Door(
    description: ItemDescription,
    itemRef: ItemRef,
    doorBehavior: CommonBehaviors.RoomLink,
    additionalBehaviors: Behavior*
  ) extends CommonItems.Door {
    override def behaviors: Seq[Behavior] = doorBehavior +: additionalBehaviors

    override def isAccessible: Boolean = doorBehavior.isAccessible
  }
}
