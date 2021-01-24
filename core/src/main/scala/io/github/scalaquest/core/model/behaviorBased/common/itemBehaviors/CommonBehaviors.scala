package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.Model

/**
 * When mixed into a [[Model]], it integrates into it the interfaces for some commonly used
 * Behaviors. These should be implemented to be used.
 */
trait CommonBehaviors extends BehaviorBasedModel {

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that can be taken and put away into the bag of the
   * player.
   */
  abstract class Takeable extends ItemBehavior

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that can be opened and closed. Open and close
   * actions should be mutually exclusives.
   */
  abstract class Openable extends ItemBehavior {
    def isOpen: Boolean
  }

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that enables the possibility to move into another
   * [[Room]]. Conceptually, an [[Item]] that exposes a [[RoomLink]] behavior could also be
   * [[Openable]].
   */
  abstract class RoomLink extends ItemBehavior {
    def isAccessible: Boolean
  }

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that can be eaten. When item is eaten shouldn't be
   * more present in the player's bag or in the current [[Room]].
   */
  abstract class Eatable extends ItemBehavior
}
