/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model

import io.github.scalaquest.core.dictionary.verbs.VerbPrep
import monocle.Lens

import scala.concurrent.Future

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
trait Model { model: Model =>

  /**
   * The specific implementation adopted for [[State]] in the specific model implementation.
   */
  type S <: State

  /**
   * The specific implementation adopted for [[Item]] in the specific model implementation.
   */
  type I <: Item

  /**
   * The specific implementation adopted for [[Ground]] in the specific model implementation.
   */
  type G <: Ground

  /**
   * The specific implementation adopted for [[Room]] in the specific model implementation.
   */
  type RM <: Room

  /**
   * The specific implementation adopted for [[Reaction]] in the specific model implementation. A
   * [[Reaction]] is a function that given a [[S]] produce a new [[S]] and a sequence of
   * [[Message]].
   */
  type Reaction = S => (S, Seq[Message])

  /** Optional serializer for this model. */
  def serializer: Option[Serializer] = None

  /**
   * A component that enables to serialize a `State` instance to the file system.
   */
  trait Serializer {

    def write(path: String, state: S): Future[Unit]

    def read(path: String): Future[S]
  }

  /**
   * Represents a snapshot of the current game, at an higher level in comparison to MatchState. The
   * key fact is that this level of abstraction can handle also [[Message]] s, that is a
   * representation of the output to render to the user at the end of the pipeline round, and all
   * the possible [[Action]] s.
   */
  abstract class State { self: S =>
    implicit val s: S = self

    /**
     * All the possible [[Action]] s that can be used into the match, associated with the Verb that
     * triggers it.
     * @return
     *   All the possible [[Action]] s, as a [[Map]].
     */
    def actions: Map[VerbPrep, Action]

    /**
     * Represents the configuration of the match, in terms of [[Model.Room]] s and [[Model.Item]].
     * @return
     *   a [[Set]] representing all [[Model.Room]] s of the match, and the [[Model.Item]] s in them.
     */
    def rooms: Map[RoomRef, RM]

    /**
     * The model's reference to the [[Ground]].
     * @return
     *   the model's instance of [[Ground]].
     */
    def ground: G

    /**
     * A [[Map]] with all the [[Model.Item]] s present in the match. For each [[ItemRef]] is found
     * the specific [[Model.Item]] in the match.
     * @return
     */
    def items: Map[ItemRef, I]

    /**
     * All the [[Model.Item]] that a Player could see.
     * @return
     */
    def scope: Set[I] = bag ++ location.items

    /**
     * References to the [[Model.Item]] s that the player brings with him. In a concrete story, this
     * is not necessarily a real bag: it is simply an intuitive term to refer to this set of items.
     * @return
     */
    def bag: Set[I]

    /**
     * Reference for the [[Model.Room]] where the player is currently positioned.
     * @return
     *   The current location of the player, as a [[RoomRef]].
     */
    def location: RM

    /**
     * Indicates whether the match has reached the end. When true, the entire match ende after the
     * current pipeline round.
     * @return
     *   True if the match has to end after the current round, false otherwise.
     */
    def ended: Boolean
  }

  /**
   * Represents a single object against which the Player can interact.
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
    def name: String              = description.mkString
    override def toString: String = name

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
    def use(action: Action, sideItem: Option[I] = None)(implicit state: S): Option[Reaction]

    /**
     * Makes items comparable based on their refs only.
     */
    override def equals(obj: Any): Boolean = this.hashCode() == obj.hashCode()

    /**
     * Makes items comparable based on their refs only.
     */
    override def hashCode(): Int = ref.hashCode()
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
    def use(action: Action)(implicit state: S): Option[Reaction]
  }

  /**
   * A geographical portion of the match map.
   *
   * This is one of the basic block for the story build by the storyteller, as it is used to
   * identify Player 's and [[Model.Item]] s' location, in a given moment of the story, and navigate
   * across the match geography.
   */
  abstract class Room { room: RM =>

    /**
     * A textual description for the room.
     * @return
     *   A textual description for the room.
     */
    def name: String
    override def toString: String = name

    /**
     * The unique identifier of the [[Room]]. This is necessary, as passing from a state to another,
     * the reference to an object changes, the [[State]] works in an immutable fashion.
     */
    def ref: RoomRef

    /**
     * Identifies [[Room]] s near to the current one, at the cardinal points.
     */
    def neighbor(direction: Direction)(implicit state: S): Option[RM]

    /**
     * A [[Map]] with all the neighbor [[Room]] of the location.
     * @param state
     *   the current state.
     * @return
     *   A [[Map]] with all the neighbor [[Room]] of the location.
     */
    def neighbors(implicit state: S): Map[Direction, RM]

    /**
     * Identifies the [[Item]] s positioned into the current [[Room]].
     * @return
     *   [[Item]] s positioned into the [[Room]] as a [[Set]]
     */
    def items(implicit state: S): Set[I]

    /**
     * Makes rooms comparable based on their refs only.
     */
    override def equals(obj: Any): Boolean = this.hashCode() == obj.hashCode()

    /**
     * Makes rooms comparable based on their refs only.
     */
    override def hashCode(): Int = ref.hashCode()
  }

  /**
   * A [[Lens]] used to retrieve the player location [[RM]] given a [[S]].
   * @return
   *   the cited above Lens.
   */
  def locationRoomLens: Lens[S, RM]

  /**
   * A [[Lens]] used to retrieve a Map with all the [[RoomRef]] to the [[RM]].
   * @return
   *   the cited above Lens.
   */
  def roomsLens: Lens[S, Map[RoomRef, RM]]

  /**
   * A [[Lens]] used to retrieve a Map with all the [[ItemRef]] to the [[I]].
   * @return
   *   the cited above Lens.
   */
  def itemsLens: Lens[S, Map[ItemRef, I]]

  /**
   * A [[Lens]] used to retrieve if the match is already ended.
   * @return
   *   the cited above Lens.
   */
  def matchEndedLens: Lens[S, Boolean]

  /**
   * A [[Lens]] used to retrieve a ItemRef.
   * @return
   *   the cited above Lens.
   */
  def bagLens: Lens[S, Set[ItemRef]]

  /**
   * A [[Lens]] used to retrieve the RoomRef referent to player location Room given a [[S]].
   * @return
   *   the cited above Lens.
   */
  def locationLens: Lens[S, RoomRef]

  /**
   * A [[Lens]] used to retrieve all the Items (indicated with ItemRef) presents in the [[RM]].
   * @return
   *   the cited above Lens.
   */
  def roomItemsLens: Lens[RM, Set[ItemRef]]

  /**
   * A [[Lens]] used to retrieve all the possible direction given a [[RM]].
   * @return
   *   the cited above Lens.
   */
  def roomDirectionsLens: Lens[RM, Map[Direction, RoomRef]]
}
