package io.github.scalaquest.core.model.behaviorBased.common.groundBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Orientate
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase

/**
 * The trait makes possible to mix into StdCommonGroundBehaviors the standard implementation of
 * Navigation.
 */
trait SimpleOrientateExt extends CommonBase {

  case class SimpleOrientate(onOrientateExtra: Option[Reaction] = None) extends Orientate {

    override def triggers: GroundTriggers = {
      // "orientate"
      case (Orientate, state) => orientate
    }

    def orientate: Reaction =
      state =>
        state.applyReactions(
          messageLens.modify(
            _ :+ Oriented(
              state.location,
              getNeighborsWithMaybeDoors(state)
            )
          ),
          onOrientateExtra.getOrElse(s => s)
        )

    def getNeighborsWithMaybeDoors(implicit state: S): Map[RM, Option[Door]] = {
      state.locationNeighbors.map(rm => (rm, getDoor(rm))).toMap
    }

    def getDoor(room: RM)(implicit state: S): Option[Door] = {
      room.items.collectFirst({ case i: Door => i })
    }
  }
}
