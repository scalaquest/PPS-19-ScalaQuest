package io.github.scalaquest.core.model

import io.github.scalaquest.core.parsing.Action

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
  }

  trait Player {
    def bag: Set[I]

    def location: Room
  }

  trait Item { item: I =>

    def name: String

    def use(action: Action): Option[Update]
  }
}
