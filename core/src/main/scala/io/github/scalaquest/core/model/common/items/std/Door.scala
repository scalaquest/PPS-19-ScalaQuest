package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.common.items.StdCommonItemsBase

trait Door extends StdCommonItemsBase {

  case class Door(name: String, doorBehavior: CommonBehaviors.RoomLink, additionalBehaviors: Set[Behavior] = Set())
    extends CommonItems.Door {
    override def behaviors: Set[Behavior] = additionalBehaviors + doorBehavior
  }
}
