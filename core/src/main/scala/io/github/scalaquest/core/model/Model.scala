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
    def useTransitive(action: Action, state: S): Option[Reaction]
    def useDitransitive(action: Action, state: S, sideItem: I): Option[Reaction]
  }
}

object ExampleUsage {
  import io.github.scalaquest.core.model.default.DefaultModel._

  // istanziare un modello (concreto), modellare storia
  DefaultCommonItems.Key("key")
}
