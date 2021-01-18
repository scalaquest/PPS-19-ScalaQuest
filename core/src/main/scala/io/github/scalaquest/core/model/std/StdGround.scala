package io.github.scalaquest.core.model.std

import io.github.scalaquest.core.model.common.groundBehaviors.{
  CommonGroundBehaviors,
  StdCommonGroundBehaviors
}

trait StdGround extends BehaviorableModel with CommonGroundBehaviors with StdCommonGroundBehaviors {

  case object StdGround extends BehaviorableGround {
    override val behaviors: Seq[GroundBehavior] = Seq(Navigation())
  }
}
