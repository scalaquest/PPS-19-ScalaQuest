package io.github.scalaquest.core.model.behaviorBased.common.pushing

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.{ItemMessage, RoomCompositionMessage, RoomMessage}
import io.github.scalaquest.core.model.Model

/**
 * Some common messages.
 */
trait CommonMessages extends BehaviorBasedModel {

  /**
   * Inspection of a specific [[Model.Room]] with the [[Model.Item]] that the room contains.
   * @param room
   *   the specific [[Model.Room]].
   * @param items
   *   the [[Model.Item]] s contained by the [[Model.Room]].
   */
  case class Inspected(room: RM, items: Set[I]) extends RoomCompositionMessage[I, RM]
  case class Eaten(item: I)                     extends ItemMessage[I]
  case class Taken(item: I)                     extends ItemMessage[I]
  case class Closed(item: I)                    extends ItemMessage[I]
  case class Opened(item: I)                    extends ItemMessage[I]
  case class Navigated(room: RM)                extends RoomMessage[RM]
}
