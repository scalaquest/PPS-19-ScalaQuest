package io.github.scalaquest.core.model

trait Message

trait Model {

  type S <: State
  type I <: Item
  type Update = S => S

  trait State { self: S =>
    def game: GameState
    def messages: Seq[Message]
  }

  trait GameState {
    def player: Player
    def ended: Boolean

    def rooms: Set[Room]
    def itemsInRooms: Map[Room, Set[I]]
  }

  trait Player {
    def bag: Set[I]
    def location: Room
  }

  trait Item { self: I =>
    def name: String
    def useTransitive(action: Action, state: State): Option[Update]
    def useDitransitive(action: Action, sideItem: Item, state: State): Option[Update]
  }

}
