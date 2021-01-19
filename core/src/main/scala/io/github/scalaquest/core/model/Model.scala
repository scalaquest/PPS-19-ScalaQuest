package io.github.scalaquest.core.model

trait Model {
  type S <: State
  type I <: Item
  type G <: Ground

  type Reaction = S => S

  trait State { self: S =>
    def game: GameState[I]
    def messages: Seq[Message]
    def extractRefs: Map[ItemRef, I]
  }

  trait Item { item: I =>
    def use(action: Action, state: S, sideItem: Option[I] = None): Option[Reaction]

    def itemRef: ItemRef
    override def hashCode(): Int = itemRef.hashCode()
  }

  trait Ground { ground: G =>
    def use(action: Action, state: S): Option[Reaction]
  }
}
