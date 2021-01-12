package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.common.items.StdCommonItemsBase
import io.github.scalaquest.core.model.common.items.StdCommonItems
import io.github.scalaquest.core.model.common.items.CommonItems

/**
 * The trait makes possible to mix into the [[StdCommonItems]] the standard implementation of
 * [[CommonItems.CommonItems.Door]].
 */
private[behaviors] trait Door extends StdCommonItemsBase {

  /**
   * Standard implementation of the common Door item.
   */
  case class Door(name: String, doorBehavior: CommonBehaviors.RoomLink, additionalBehaviors: Set[Behavior] = Set())
    extends CommonItems.Door {
    override def behaviors: Set[Behavior] = additionalBehaviors + doorBehavior
  }
}
