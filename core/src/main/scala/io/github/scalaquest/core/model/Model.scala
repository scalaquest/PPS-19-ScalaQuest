package io.github.scalaquest.core.model

trait Message

trait Model {
  type S <: State
  type I <: Item

  type Self     = this.type
  type Reaction = Self#S => Self#S

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

  trait Item { item: I =>
    def name: String
    def useTransitive[SS <: Model#S, RR <: Model#Reaction](action: Action, state: SS): Option[RR]

    def useDitransitive[SS <: Model#S, II <: Model#I, RR <: Model#Reaction](
      action: Action,
      sideItem: II,
      state: SS
    ): Option[RR]
  }
}
