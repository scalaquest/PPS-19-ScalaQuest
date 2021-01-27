package io.github.scalaquest.core.model.behaviorBased.common.messages

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.{ItemMessage, RoomMessage}

trait CommonMessages extends BehaviorBasedModel {

  case class Eaten(item: I)      extends ItemMessage[I]
  case class Taken(item: I)      extends ItemMessage[I]
  case class Closed(item: I)     extends ItemMessage[I]
  case class Opened(item: I)     extends ItemMessage[I]
  case class Navigated(room: RM) extends RoomMessage[RM]
}
