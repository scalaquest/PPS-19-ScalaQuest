package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.common.groundBehaviors.{
  CommonGroundBehaviors,
  CommonGroundBehaviorsImpl
}

trait StdGround
  extends BehaviorBasedModel
  with CommonGroundBehaviors
  with CommonGroundBehaviorsImpl {

  case object StdGround extends BehaviorBasedGround {
    override val behaviors: Seq[GroundBehavior] = Seq(Navigation())
  }
}
