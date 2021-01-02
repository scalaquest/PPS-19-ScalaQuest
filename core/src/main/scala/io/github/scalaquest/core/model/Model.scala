package io.github.scalaquest.core.model

trait Message

trait Model {
  type S <: State
  type I <: Item

  type Self   = this.type
  type Update = Self#S => Self#S

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
    def useTransitive[SS <: Model#S, UU <: Model#Update](action: Action, state: SS): Option[UU]

    def useDitransitive[SS <: Model#S, II <: Model#I, UU <: Model#Update](
      action: Action,
      sideItem: II,
      state: SS
    ): Option[UU]
  }
}
