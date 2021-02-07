package io.github.scalaquest.core.model.behaviorBased.common.pushing

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.{ItemMessage, RoomMessage}
import io.github.scalaquest.core.model.behaviorBased.common.items.CommonItems

/**
 * Some common messages.
 */
trait CommonMessages extends BehaviorBasedModel with CommonItems {
  case class Inspected(room: RM, items: Set[I])                   extends RoomMessage[RM]
  case class Oriented(room: RM, neighbors: Map[RM, Option[Door]]) extends RoomMessage[RM]
  case class Navigated(room: RM)                                  extends RoomMessage[RM]

  case class Eaten(item: I)  extends ItemMessage[I]
  case class Taken(item: I)  extends ItemMessage[I]
  case class Closed(item: I) extends ItemMessage[I]
  case class Opened(item: I) extends ItemMessage[I]
}
