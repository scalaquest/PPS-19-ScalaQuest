package io.github.scalaquest.core.model

trait Message

trait Model {

  type S <: State
  type I <: Item
  type Update   = S => S
  type Property = PartialFunction[(Action, Item, S), Update]

  trait State { self: S =>
    def game: GameState
    def messages: Seq[Message]
  }

  trait GameState {
    def player: Player
    def ended: Boolean
  }

  trait Player {
    def bag: Set[I]
    def location: Room
  }

  trait Item { item: I =>
    def name: String
    var properties: Set[Property] = Set()
    def use(action: Action, state: S): Option[Update]
  }

}
