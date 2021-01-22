package io.github.scalaquest.core.model

import io.github.scalaquest.core.pipeline.parser.ItemDescription

trait Model {
  type S <: State
  type I <: Item
  type G <: Ground

  type Reaction = S => S

  trait State { self: S =>
    def game: GameState[I]
    def actions: Map[String, Action]
    def messages: Seq[Message]
    def extractRefs: Map[ItemRef, I]
  }

  trait Item { item: I =>
    def use(action: Action, state: S, sideItem: Option[I] = None): Option[Reaction]

    def description: ItemDescription
    def itemRef: ItemRef
    override def hashCode(): Int = itemRef.hashCode()
  }

  trait Ground { ground: G =>
    def use(action: Action, state: S): Option[Reaction]
  }
}
