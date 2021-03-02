package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl.{
  GenericGroundBehaviorExt,
  InspectableBagExt,
  InspectableLocationExt,
  NavigableExt
}

/**
 * When mixed into a Model, it enables the implementation for the common behaviors provided by
 * ScalaQuest Core. It requires the storyteller to implement all the required [[monocle.Lens]], used
 * by the implementation to access and re-generate the concrete State.
 *
 * This trait enable the player to inspect his bag, a room and he can also move in another room.
 */
trait CGroundBehaviorsExt
  extends BehaviorBasedModel
  with NavigableExt
  with InspectableLocationExt
  with InspectableBagExt
  with GenericGroundBehaviorExt
