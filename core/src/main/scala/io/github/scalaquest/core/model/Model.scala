package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.common.{CommonBehaviors, CommonBehaviorsAndItems}
import io.github.scalaquest.core.model.default.BehaviorableModel

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

trait AdvancedState extends Model {

  override type S = ConcreteIntermediateState

  case class ConcreteIntermediateState() extends State {
    override def game: GameState[I] = ???

    override def messages: Seq[Message] = ???
  }
}

object ConcreteModel extends BehaviorableModel with CommonBehaviorsAndItems with AdvancedState

object main {

  import ConcreteModel._

}
