package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.common.groundBehaviors.{
  CommonGroundBehaviors,
  SimpleCommonGroundBehaviors
}

trait SimpleGround
  extends BehaviorBasedModel
  with CommonGroundBehaviors
  with SimpleCommonGroundBehaviors {

  case object SimpleGround extends BehaviorBasedGround {
    override val behaviors: Seq[GroundBehavior] = Seq(SimpleNavigation())
  }
}
