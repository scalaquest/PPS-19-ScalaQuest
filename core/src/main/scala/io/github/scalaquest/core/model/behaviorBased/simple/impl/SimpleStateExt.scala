package io.github.scalaquest.core.model.behaviorBased.simple.impl

import io.github.scalaquest.core.dictionary.verbs.VerbPrep
import io.github.scalaquest.core.model._
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.grounds.CGroundExt
import monocle.Lens
import monocle.macros.GenLens

/**
 * Extension for the model. Adds a base implementation of the [[Model.State]].
 */
trait SimpleStateExt extends BehaviorBasedModel with CGroundExt {

  override type S = SimpleState

  /**
   * Class that implement the [[Model.State]] interface.
   * @param actions
   *   a Map that found for each verb the specific [[Action]].
   * @param rooms
   *   a Map that found for each room reference the updated Room.
   * @param items
   *   a Map that found for each item reference the updated Item.
   * @param ground
   *   the ground implementation used in this State.
   * @param _bag
   *   the player's bag reference.
   * @param _location
   *   the room reference where player start the match.
   * @param ended
   *   true if game is ended, false otherwise.
   */
  case class SimpleState(
    actions: Map[VerbPrep, Action],
    rooms: Map[RoomRef, RM],
    items: Map[ItemRef, I],
    ground: G,
    _bag: Set[ItemRef],
    _location: RoomRef,
    ended: Boolean = false
  ) extends State {
    override def bag: Set[I]  = _bag.flatMap(items.get)
    override def location: RM = rooms(_location)
  }

  /**
   * Companion object with apply for SimpleState.
   */
  object State {

    /**
     * Facility methods for SimpleState.
     * @param actions
     *   a Map that found for each verb the specific [[Action]].
     * @param rooms
     *   a Map that found for each room reference the updated Room.
     * @param items
     *   a Map that found for each item reference the updated Item.
     * @param ground
     *   the ground implementation used in this State.
     * @param ended
     *   true if game is ended, false otherwise.
     * @return
     *   the instance of SimpleState.
     */
    def apply(
      actions: Map[VerbPrep, Action],
      rooms: Map[RoomRef, RM],
      items: Map[ItemRef, I],
      ground: G = CGround(),
      bag: Set[ItemRef] = Set.empty,
      location: RoomRef,
      ended: Boolean = false
    ): S = SimpleState(actions, rooms, items, ground, bag, location, ended)
  }

  override def roomsLens: Lens[S, Map[RoomRef, RM]] = GenLens[S](_.rooms)
  override def itemsLens: Lens[S, Map[ItemRef, I]]  = GenLens[S](_.items)
  override def matchEndedLens: Lens[S, Boolean]     = GenLens[S](_.ended)
  override def bagLens: Lens[S, Set[ItemRef]]       = GenLens[S](_._bag)
  override def locationLens: Lens[S, RoomRef]       = GenLens[S](_._location)
}
