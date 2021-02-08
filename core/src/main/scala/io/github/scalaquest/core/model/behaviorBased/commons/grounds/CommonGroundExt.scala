package io.github.scalaquest.core.model.behaviorBased.commons.grounds

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.CommonGroundBehaviorsExt

/**
 * Extension for the model, based on the behavior-based fashion. Adds a base implementation of the
 * [[BehaviorBasedModel.Ground]] with common behaviors inside, extensible on demand with more
 * behaviors.
 */
trait CommonGroundExt extends BehaviorBasedModel with CommonGroundBehaviorsExt {

  case class CommonGround(additionalBehaviors: Seq[GroundBehavior] = Seq())
    extends BehaviorBasedGround {

    override val behaviors: Seq[GroundBehavior] =
      additionalBehaviors ++ Seq(Inspectable(), Navigable(), Orientable())
  }
}
