package io.github.scalaquest.core.model.common.behaviors

import io.github.scalaquest.core.model.std.BehaviorableModel
import io.github.scalaquest.core.model.Model

/**
 * When mixed into a [[Model]], it integrates into it the interfaces for some commonly used
 * [[BehaviorableModel.Behavior]]s. These should be implemented to be used.
 */
trait CommonBehaviors extends BehaviorableModel {

  /**
   * Interfaces for commonly used [[BehaviorableModel.Behavior]]s.
   */
  object CommonBehaviors {

    /**
     * A [[Behavior]] associated to an [[Item]] that can be taken and put away into the bag of the
     * player.
     */
    abstract class Takeable extends Behavior

    /**
     * A [[Behavior]] associated to an [[Item]] that that can be opened and closed. Open and close actions should be
     * mutually exclusives.
     */
    abstract class Openable extends Behavior {
      def isOpen: Boolean
    }

    /**
     * A [[Behavior]] associated to an [[Item]] that enables the possibility to move into another [[Room]].
     * Conceptually, an [[Item]] that exposes a [[RoomLink]] behavior could also be [[Openable]].
     */
    abstract class RoomLink extends Behavior
  }
}
