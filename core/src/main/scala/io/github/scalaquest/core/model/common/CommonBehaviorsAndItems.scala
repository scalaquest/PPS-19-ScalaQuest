package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.default.BehaviorableModel

trait ItemCategories extends BehaviorableModel {

  object Categories {
    trait Door extends BehaviorableItem
    trait Key  extends BehaviorableItem
  }
}

trait CommonBehaviorsAndItems extends CommonBehaviors {

  object Items {
    case class Key(name: String, override val behaviors: Set[Behavior] = Set()) extends Categories.Key

    case class Door(name: String, doorBehavior: RoomLink) extends Categories.Door {
      def additionalBehaviors: Set[Behavior] = Set()
      override def behaviors: Set[Behavior]  = additionalBehaviors + doorBehavior
    }

    case class GenericItem(name: String, override val behaviors: Set[Behavior] = Set()) extends BehaviorableItem

  }

  // ecc..

  // implementation: this a fragment of the 'storyteller' part, to put into the example
  //val kitchen: Room = Room("kitchen", () => Map[Direction, Room]())
  //  val cup: GenericItem = GenericItem("cup", Set(Takeable()))
  //val kitchenKey: Key = Key("kitchen's key")
  //val door: Door      = Door("kitchen's door", RoomLink(kitchen, Openable(needsKey = Some(kitchenKey))))

}
