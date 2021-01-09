package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.Actions.Enter
import io.github.scalaquest.core.model.common.behaviors.StdCommonBehaviorsBase
import monocle.Lens

trait RoomLink extends StdCommonBehaviorsBase {

  /**
   * The behavior of a door, for example: with transitive action Enter, it moves the player into
   * another room.
   */
  case class RoomLink(
    endRoom: Room,
    openable: CommonBehaviors.Openable,
    onEnterExtra: Option[Reaction] = None
  )(
    implicit currRoomLens: Lens[S, Room]
  ) extends CommonBehaviors.RoomLink
    with Composable
    with ExtraUtils {

    override def superBehavior: Behavior = openable

    override def baseTrigger: Triggers = {
      case (Enter, _, None, _) if openable.isOpen => enterRoom()
    }

    def enterRoom(): Reaction =
      state => {
        val updLocState = currRoomLens.modify(_ => endRoom)(state)
        applyExtraIfPresent(onEnterExtra)(updLocState)
      }
  }
}
