package io.github.scalaquest.core.model.std

class StdModelTest {
  import io.github.scalaquest.core.model.std.StdModel._
  import io.github.scalaquest.core.model._
  import io.github.scalaquest.core.model.Direction.Direction

  // implementation: this a fragment of the 'storyteller' part, to put into the example
  val kitchen: Room = Room("kitchen", () => Map[Direction, Room]())

  val cup: GenericItem = GenericItem("cup", Takeable())

  val kitchenKey: Key = Key("kitchen's key")
  val door: Door      = Door("kitchen's door", RoomLink(kitchen, Some(Openable(requiredKey = Some(kitchenKey)))))
}
