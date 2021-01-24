package io.github.scalaquest.core.model

trait Model {
  type S <: State
  type I <: Item
  type G <: Ground

  type Reaction = S => S

  /**
   * Represents a snapshot of the current game, at an higher level in comparison to [[MatchState]],
   * including the state of the game. The key fact is that this level of abstraction can handle also
   * [[Message]] s, that is a representation of the output to render to the user at the end of the
   * pipeline round.
   */
  abstract class State { self: S =>

    def actions: Map[String, Action]

    /**
     * The state of the game, in a vision scoped to the [[Player]] capabilities.
     *
     * @return
     *   The [[MatchState]] of the match.
     */
    def matchState: MatchState[I]

    /**
     * A representation of the output to render to the user at the end of the pipeline round.
     * @return
     *   A [[Seq]] of [[Message]] s.
     */
    def messages: Seq[Message]

    /**
     * A method that extracts a [[Map]] that links all the [[Item]] inside the [[State]] to their
     * [[ItemRef]] s. This should be implemented for each concrete [[Model]] implementation.
     * @return
     *   A [[Map]] from [[ItemRef]] to [[Item]].
     */
    def extractRefs: Map[ItemRef, I]
  }

  /**
   * Represents a single object against which the [[Player]] can interact.
   */
  abstract class Item { item: I =>

    def description: ItemDescription

    /**
     * The unique identifier of the [[Item]]. This is necessary, as passing from a state to another,
     * the reference to an object changes, the [[State]] works in an immutable fashion.
     */
    def itemRef: ItemRef

    /**
     * The hash code of the [[Item]] is overridden in a way that delegates to the Item's [[ItemRef]]
     * the creation of the hash code. This enables the possibility of make a match between a same
     * [[Item]] from different [[State]] s.
     * @return
     *   The Item's [[ItemRef]] hashcode.
     */
    final override def hashCode(): Int = itemRef.hashCode()

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
     */
    def use(action: Action, state: S, sideItem: Option[I] = None): Option[Reaction]
  }

  abstract class Ground { ground: G =>
    def use(action: Action, state: S): Option[Reaction]
  }
}
