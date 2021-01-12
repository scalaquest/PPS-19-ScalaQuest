package io.github.scalaquest.core.model

trait Model {
  type S <: State
  type I <: Item

  type Reaction = S => S

  trait State { self: S =>
    def game: GameState[I]
    def messages: Seq[Message]
  }

  trait Item { item: I =>
    def name: String
    def use(action: Action, state: S, sideItem: Option[I] = None): Option[Reaction]
  }
}

object ExampleUsage {
  import io.github.scalaquest.core.model.std.StdModel._
  import io.github.scalaquest.core.model.Direction.Direction

  // implementation: this a fragment of the 'storyteller' part, to put into the example
  val kitchen: Room = Room("kitchen", () => Map[Direction, Room]())

  val cup: GenericItem = GenericItem("cup", Set(Takeable()))

  val kitchenKey: Key = Key("kitchen's key")
  val door: Door      = Door("kitchen's door", RoomLink(kitchen, Some(Openable(requiredKey = Some(kitchenKey)))))
}
