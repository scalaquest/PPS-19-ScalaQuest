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

    /*
    def useTransitive[SS <: S](action: Action, state: SS): Option[Reaction]
    def useDitransitive[SS <: S, II <: I](action: Action, sideItem: II, state: SS): Option[Reaction]
     */

    ////// fixme experiments

    val model: Self

    //def useTransitive[SS <: Model#S](action: Action, state: SS): Option[state.Reaction]

    //def useTransitive2[SS <: Model#S](action: Action, state: SS): Unit

    //  def useTransitive3(action: Action, state: model.S): Option[state.Reaction]
    def useTransitive4(action: Action, state: model.S): Option[model.Reaction]
    /*def useDitransitive[SS <: Model#S, II <: Model#I](
      action: Action,
      sideItem: II,
      state: SS
    ): Option[state.Reaction]

     */

    ////// fixme end of experiments
  }
}
