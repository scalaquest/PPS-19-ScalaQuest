package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl.{
  InspectExt,
  NavigableExt,
  OrientableExt
}

/**
 * When mixed into a [[Model]], it enables the implementation for the common behaviors provided by
 * ScalaQuest Core. It requires the storyteller to implement all the required [[monocle.Lens]], used
 * by the implementation to access and re-generate the concrete [[Model.State]].
 */
trait CommonGroundBehaviorsExt
  extends BehaviorBasedModel
  with NavigableExt
  with InspectExt
  with OrientableExt
