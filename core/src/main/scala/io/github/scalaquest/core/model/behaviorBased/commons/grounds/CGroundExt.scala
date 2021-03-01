package io.github.scalaquest.core.model.behaviorBased.commons.grounds

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.CGroundBehaviorsExt

/**
 * Extension for the model, based on the behavior-based fashion. Adds a base implementation of the
 * <b>BehaviorBasedGround</b> with common behaviors inside, extensible on demand with more
 * behaviors.
 */
trait CGroundExt extends BehaviorBasedModel with CGroundBehaviorsExt {

  /**
   * A <b>BehaviorBasedGround</b> implementation that includes all the common
   * <b>GroundBehaviors</b>.
   * @param additionalBehaviors
   *   <b>GroundBehaviors</b> to include in addition to the common ones.
   */
  case class CGround(additionalBehaviors: Seq[GroundBehavior] = Seq.empty)
    extends BehaviorBasedGround {

    override val behaviors: Seq[GroundBehavior] =
      additionalBehaviors ++ Seq(InspectableLocation(), InspectableBag(), Navigable())
  }
}
