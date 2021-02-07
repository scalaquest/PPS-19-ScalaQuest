package io.github.scalaquest.core.model.behaviorBased.commons.pushing

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.{Direction, ItemMessage, RoomMessage}
import io.github.scalaquest.core.model.behaviorBased.commons.items.CommonItemsExt

/**
 * Some common messages.
 */
trait CommonMessagesExt extends BehaviorBasedModel with CommonItemsExt {
  case class Inspected(room: RM, items: Set[I])                extends RoomMessage[RM]
  case class Oriented(room: RM, neighbors: Map[Direction, RM]) extends RoomMessage[RM]
  case class Navigated(room: RM)                               extends RoomMessage[RM]

  case class Eaten(item: I)  extends ItemMessage[I]
  case class Taken(item: I)  extends ItemMessage[I]
  case class Closed(item: I) extends ItemMessage[I]
  case class Opened(item: I) extends ItemMessage[I]
}
