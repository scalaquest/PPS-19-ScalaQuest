package io.github.scalaquest.core.model.common.items.impl

import io.github.scalaquest.core.model.common.items.DefaultCommonItemsBase

trait Door extends DefaultCommonItemsBase {

  case class Door(name: String, doorBehavior: CommonBehaviors.RoomLink, additionalBehaviors: Set[Behavior] = Set())
    extends CommonItems.Door {
    override def behaviors: Set[Behavior] = additionalBehaviors + doorBehavior
  }
}
