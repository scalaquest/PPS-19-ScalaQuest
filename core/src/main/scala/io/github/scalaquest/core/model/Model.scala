package io.github.scalaquest.core.model

import monocle.Lens

/**
 * A way to represent the basic linked concepts of the story, in an extendible way. Usage example:
 * {{{
 *
 *   object MyModel extends Model with SimpleState with SimpleGround with MyItems
 *   ...
 *   val myItemImpl = MyModel.MySword("a sword!", Weapon())
 *   ...
 * }}}
 */
trait Model {
  type S <: State
  type I <: Item
  type G <: Ground
  type RM <: Room

  type Reaction = S => S

  /**
   * Represents a snapshot of the current game, at an higher level in comparison to [[MatchState]].
   * The key fact is that this level of abstraction can handle also [[Message]] s, that is a
   * representation of the output to render to the user at the end of the pipeline round, and all
   * the possible [[Action]] s.
   */
  abstract class State { self: S =>

    /**
     * All the possible [[Action]] s that can be used into the match, associated with the Verb that
     * triggers it.
     * @return
     *   All the possible [[Action]] s, as a [[Map]].
     */
    def actions: Map[String, Action]

    /**
     * The state of the game, in a vision scoped to the player capabilities.
     * @return
     *   The [[MatchState]] of the match.
     */
    def matchState: MatchState[I, RM]

    /**
     * A representation of the occurred events in a given the pipeline round. The storyteller can
     * then decide what to show for each one.
     * @return
     *   A [[Seq]] of [[Message]] s.
     */
    def messages: Seq[Message]
  }

  /**
   * Represents a single object against which the [[Player]] can interact.
   */
  abstract class Item { item: I =>

    /**
     * Semantic description of the object.
     * @return
     *   An [[ItemDescription]] describing the semantic of the object.
     */
    def description: ItemDescription

    /**
     * A textual representation of the [[Item]].
     * @return
     */
    def name: String = description.mkString

    /**
     * The unique identifier of the [[Item]]. This is necessary, as passing from a state to another,
     * the reference to an object changes, the [[State]] works in an immutable fashion.
     */
    def ref: ItemRef

    /**
     * Define a way make the item interact with the [[State]]. The interaction is founded into the
     * [[Action]] (ex. 'open the door'), but it can include also an additional [[Item]] ('open the
     * door with the key'). The item should react to each combination with an appropriate
     * [[Reaction]].
     * @param action
     *   An action from a statement (ex. <b>open</b> the door with a key).
     * @param state
     *   the starting state, useful to give a context to the decision to take.
     * @param sideItem
     *   A side [[Item]] from a statement (ex. open the door with <b>a key</b>).
     * @return
     *   The resulting [[Reaction]] from the combination, or a [[None]] if the match fails.
     */
    def use(action: Action, state: S, sideItem: Option[I] = None): Option[Reaction]
  }

  /**
   * The ground is a sort of "virtual" item, that handles the intransitive [[Action]] s linked to
   * the match.
   */
  abstract class Ground { ground: G =>

    /**
     * Define a way make the given [[Action]] interacts with the [[State]]. The interaction is
     * founded into a single [[Action]] value (ex. 'inspect'). The ground should react to each
     * combination with an appropriate [[Reaction]].
     * @param action
     *   An action from a statement (ex. <b>inspect</b> ).
     * @param state
     *   the starting state, useful to give a context to the decision to take.
     * @return
     *   The resulting [[Reaction]] from the combination, or a [[None]] if the match fails.
     */
    def use(action: Action, state: S): Option[Reaction]
  }

  /**
   * A geographical portion of the match map.
   *
   * This is one of the basic block for the story build by the storyteller, as it is used to
   * identify [[Player]] 's and [[Model.Item]] s' location, in a given moment of the story, and
   * navigate across the match geography.
   */
  abstract class Room { room: RM =>

    /**
     * A textual identifier for the room.
     * @return
     *   A textual identifier for the room.
     */
    def name: String

    /**
     * The unique identifier of the [[Room]]. This is necessary, as passing from a state to another,
     * the reference to an object changes, the [[State]] works in an immutable fashion.
     */
    def ref: RoomRef

    /**
     * Identifies [[Room]] s near to the current one, at the cardinal points.
     */
    def neighbor(direction: Direction): Option[RoomRef]

    /**
     * Identifies the [[Item]] s positioned into the current [[Room]].
     * @return
     *   [[Item]] s positioned into the [[Room]] as a [[Set]]
     */
    def items: Set[ItemRef]
  }

  /**
   * Lens used to empty [[Message]] s after each pipeline round.
   * @return
   */
  implicit def messageLens: Lens[S, Seq[Message]]
}
