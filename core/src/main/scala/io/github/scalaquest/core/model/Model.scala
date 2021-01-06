package io.github.scalaquest.core.model

trait Message

trait GameState[I] {
  def player: Player[I]
  def ended: Boolean

  def rooms: Set[Room]
  def itemsInRooms: Map[Room, Set[I]]
}

trait Player[I] {
  def bag: Set[I]
  def location: Room
}

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

trait BehaviorableModel extends Model {
  override type I = BehaviorableItem

  type TransitiveTriggers   = PartialFunction[(Action, I, S), Reaction]
  type DitransitiveTriggers = PartialFunction[(Action, I, I, S), Reaction]

  trait Behavior {
    def triggers: TransitiveTriggers               = PartialFunction.empty
    def ditransitiveTriggers: DitransitiveTriggers = PartialFunction.empty
  }

  trait BehaviorableItem extends Item {
    def behaviors: Set[Behavior] = Set()

    override def useTransitive(action: Action, state: S): Option[Reaction] =
      behaviors.map(_.triggers).reduce(_ orElse _).lift((action, this, state))

    override def useDitransitive(action: Action, state: S, sideItem: I): Option[Reaction] =
      behaviors.map(_.ditransitiveTriggers).reduce(_ orElse _).lift((action, this, sideItem, state))

  }
}

trait HasItems extends Model { self: BehaviorableModel =>

  object items {
    case class Key(name: String, override val behaviors: Set[Behavior] = Set()) extends BehaviorableItem

    case class Door(name: String) extends BehaviorableItem {
      override def behaviors = Set()
    }

    case class GenericItem(name: String, override val behaviors: Set[Behavior] = Set()) extends BehaviorableItem
  }
}

trait AdvancedState extends Model {

  override type S = ConcreteIntermediateState

  case class ConcreteIntermediateState() extends State {
    override def game: GameState[I] = ???

    override def messages: Seq[Message] = ???
  }
}

object ConcreteModel extends BehaviorableModel with HasItems with AdvancedState

object main {

  import ConcreteModel.items
  items.Key("ciao")

}
