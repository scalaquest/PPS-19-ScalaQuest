package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.default.BehaviorableModel

/**
 * This is a mixable part of the model, that adds some implemented common items to the model.
 */
trait DefaultCommonItems extends BehaviorableModel with CommonBehaviors with CommonItems {

  case class Key(name: String, additionalBehaviors: Set[Behavior] = Set()) extends CommonItems.Key {
    override val behaviors: Set[Behavior] = additionalBehaviors
  }

  case class Door(name: String, doorBehavior: CommonBehaviors.RoomLink, additionalBehaviors: Set[Behavior] = Set())
    extends CommonItems.Door {
    override def behaviors: Set[Behavior] = additionalBehaviors + doorBehavior
  }

  case class GenericItem(name: String, override val behaviors: Set[Behavior] = Set()) extends CommonItems.GenericItem

}
