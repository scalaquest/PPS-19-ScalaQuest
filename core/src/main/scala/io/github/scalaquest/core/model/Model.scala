package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.default.DefaultModel

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
    def useTransitive(action: Action, state: S): Option[Reaction]
    def useDitransitive(action: Action, state: S, sideItem: I): Option[Reaction]
  }
}

object ExampleUsage {
  import io.github.scalaquest.core.model.default.DefaultModel._
  import io.github.scalaquest.core.model.Direction.Direction

  // implementation: this a fragment of the 'storyteller' part, to put into the example
  val kitchen: Room = Room("kitchen", () => Map[Direction, Room]())

  val cup: GenericItem = GenericItem("cup", Set(Takeable()))

  val kitchenKey: Key = Key("kitchen's key")
  val door: Door      = Door("kitchen's door", RoomLink(kitchen, Openable(requiredKey = Some(kitchenKey))))
}
