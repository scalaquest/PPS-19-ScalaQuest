package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.default.BehaviorableModel

/**
 * This is a mixable part of the model, that adds some implemented common items to the model.
 */
trait DefaultCommonItems extends BehaviorableModel with CommonBehaviors with CommonItems {

  object DefaultCommonItems {

    case class Key(name: String, additionalBehaviors: Set[Behavior] = Set()) extends CommonItems.Key {
      override val behaviors: Set[Behavior] = additionalBehaviors
    }

    case class Door(name: String, doorBehavior: CommonBehaviors.RoomLink, additionalBehaviors: Set[Behavior] = Set())
      extends CommonItems.Door {
      override def behaviors: Set[Behavior] = additionalBehaviors + doorBehavior
    }

    case class GenericItem(name: String, override val behaviors: Set[Behavior] = Set()) extends CommonItems.GenericItem

    // ecc..

    // implementation: this a fragment of the 'storyteller' part, to put into the example
    //val kitchen: Room = Room("kitchen", () => Map[Direction, Room]())
    //  val cup: GenericItem = GenericItem("cup", Set(Takeable()))
    //val kitchenKey: Key = Key("kitchen's key")
    //val door: Door      = Door("kitchen's door", RoomLink(kitchen, Openable(needsKey = Some(kitchenKey))))
  }
}
