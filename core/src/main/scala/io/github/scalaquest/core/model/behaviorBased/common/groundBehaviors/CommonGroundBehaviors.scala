package io.github.scalaquest.core.model.behaviorBased.common.groundBehaviors

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel

/**
 * When mixed into a [[Model]], it integrates into it the interfaces for some commonly used
 * GroundBehavior s (basically, intransitive actions). These should be implemented to be used.
 */
trait CommonGroundBehaviors extends BehaviorBasedModel {

  /**
   * A [[GroundBehavior]] that enables the possibility to navigate Rooms using Directions.
   */
  abstract class Navigation extends GroundBehavior

  /**
   * A [[GroundBehavior]] that enables the possibility to know the items present into the current
   * Room.
   */
  abstract class Inspect extends GroundBehavior
}
