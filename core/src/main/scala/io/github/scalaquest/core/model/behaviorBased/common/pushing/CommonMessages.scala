package io.github.scalaquest.core.model.behaviorBased.common.pushing

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.{ItemMessage, RoomConfigMessage, RoomMessage}

trait CommonMessages extends BehaviorBasedModel {
  case class Inspected(room: RM, items: Set[I]) extends RoomConfigMessage[I, RM]
  case class Eaten(item: I)                     extends ItemMessage[I]
  case class Taken(item: I)                     extends ItemMessage[I]
  case class Closed(item: I)                    extends ItemMessage[I]
  case class Opened(item: I)                    extends ItemMessage[I]
  case class Navigated(room: RM)                extends RoomMessage[RM]
}
