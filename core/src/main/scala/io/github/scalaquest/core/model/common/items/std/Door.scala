package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.common.items.{
  StdCommonItemsBase,
  StdCommonItems,
  CommonItems
}

/**
 * The trait makes possible to mix into the [[StdCommonItems]] the standard implementation of
 * [[CommonItems.CommonItems.Door]].
 */
trait Door extends StdCommonItemsBase {

  /**
   * Standard implementation of the common Door item.
   */
  case class Door(
    name: String,
    doorBehavior: CommonBehaviors.RoomLink,
    additionalBehaviors: Behavior*
  ) extends CommonItems.Door {
    override def behaviors: Seq[Behavior] = doorBehavior +: additionalBehaviors
  }
}
