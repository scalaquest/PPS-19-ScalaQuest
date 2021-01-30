package io.github.scalaquest.core.model.behaviorBased.common.groundBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Inspect
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.Message
import monocle.Lens

/**
 * The trait makes possible to mix into StdCommonGroundBehaviors the standard implementation of
 * Navigation.
 */
trait SimpleInspectExt extends CommonBase {

  case class SimpleInspect(onInspectExtra: Option[Reaction] = None)(implicit
    messageLens: Lens[S, Seq[Message]]
  ) extends Inspect {

    override def triggers: GroundTriggers = {
      // "inspect"
      case (Inspect, state) => inspectRoom(state.currentRoom)
    }

    def inspectRoom(targetRoom: RM): Reaction =
      state =>
        state.applyReactions(
          messageLens.modify(_ :+ Inspected(targetRoom, state.itemsFromRefs(targetRoom.items))),
          onInspectExtra.getOrElse(s => s)
        )
  }
}
