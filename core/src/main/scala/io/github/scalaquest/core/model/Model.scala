package io.github.scalaquest.core.model

trait Message

trait Model {

  type S <: State

  type I <: Item

  type Effect = S => S

  trait State { self: S =>
    def game: Game

    def messages: Seq[Message]
  }

  trait Game {
    def player: Player

    def ended: Boolean
  }

  trait Player {
    def bag: Set[I]

    def location: Room
  }

  trait Item { item: I =>

    def name: String

    def use(action: Action): Option[Effect]
  }
}
