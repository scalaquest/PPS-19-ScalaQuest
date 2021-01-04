package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.Direction.Direction
import io.github.scalaquest.core.model.common.Behaviors.{Openable, RoomLink, Takeable}
import io.github.scalaquest.core.model.impl.Behavior.Behavior
import io.github.scalaquest.core.model.impl.SimpleModel.{BehaviorableItem}
import io.github.scalaquest.core.model.Room

object Items {

  // the case class could be useful to implement a category of object
  case class Key(name: String, override val behaviors: Set[Behavior] = Set()) extends BehaviorableItem

  case class Door(name: String, roomLinkBehavior: RoomLink) extends BehaviorableItem {
    override def behaviors = Set(roomLinkBehavior)
  }

  case class GenericItem(name: String, override val behaviors: Set[Behavior] = Set()) extends BehaviorableItem

  // ecc..

  // implementation: this a fragment of the 'storyteller' part, to put into the example
  val kitchen: Room = Room("kitchen", () => Map[Direction, Room]())

  val cup: GenericItem = GenericItem("cup", Set(Takeable()))
  val kitchenKey: Key  = Key("kitchen's key")
  val door: Door       = Door("kitchen's door", RoomLink(kitchen, Openable(needsKey = Some(kitchenKey))))

  val treasure: GenericItem =
    GenericItem("treasure", Set(Openable(needsKey = Some(kitchenKey), onOpenExtra = Some(state => state))))
}
