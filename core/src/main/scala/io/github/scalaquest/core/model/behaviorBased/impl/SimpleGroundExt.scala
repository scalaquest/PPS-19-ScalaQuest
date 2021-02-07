package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.{
  CommonGroundBehaviorsExt,
  CommonSimpleGroundBehaviorsExt
}

/**
 * Extension for the model, based on the behavior-based fashion. Adds a base implementation of the
 * [[BehaviorBasedModel.Ground]].
 */
trait SimpleGroundExt extends BehaviorBasedModel with CommonSimpleGroundBehaviorsExt {

  case object SimpleGround extends BehaviorBasedGround {

    override val behaviors: Seq[GroundBehavior] =
      allSimpleCommonGroundBehaviors
  }
}
