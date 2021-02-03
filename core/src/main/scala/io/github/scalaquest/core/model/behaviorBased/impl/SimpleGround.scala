package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.Action.Common.Inspect
import io.github.scalaquest.core.model.{Action, InspectedRoom, NotRecognizedMessage}
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

    val inspection: GroundBehavior = new GroundBehavior {

      override def triggers: GroundTriggers = { case (Inspect, s) =>
        messageLens.modify(
          _.appended(
            s.roomFromRef(s.matchState.player.location)
              .map(InspectedRoom)
              .getOrElse(NotRecognizedMessage)
          )
        )
      }
    }
    override val behaviors: Seq[GroundBehavior] = Seq(SimpleNavigation(), inspection)
  }
}
