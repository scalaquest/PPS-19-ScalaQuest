package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl.{
  InspectableExt,
  NavigableExt
}

/**
 * When mixed into a Model, it enables the implementation for the common behaviors provided by
 * ScalaQuest Core. It requires the storyteller to implement all the required [[monocle.Lens]], used
 * by the implementation to access and re-generate the concrete State.
 */
trait CommonGroundBehaviorsExt extends BehaviorBasedModel with NavigableExt with InspectableExt
