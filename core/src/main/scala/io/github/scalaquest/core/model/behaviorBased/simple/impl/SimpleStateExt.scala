/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

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
   *   [[Map]] that found for each <b>verb</b> the specific [[Action]].
   * @param rooms
   *   [[Map]] that found for each <b>room</b> reference the updated [[Room]].
   * @param items
   *   [[Map]] that found for each <b>item</b> reference the updated [[Item]].
   * @param ground
   *   <b>Ground</b> implementation used in this <b>State</b>.
   * @param _bag
   *   Player's <b>bag</b> reference.
   * @param _location
   *   <b>Room</b> reference where player start the match.
   * @param ended
   *   True if game is ended, false otherwise.
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
     *   [[Map]] that found for each <b>verb</b> the specific [[Action]].
     * @param rooms
     *   [[Map]] that found for each <b>room</b> reference the updated [[Room]].
     * @param items
     *   [[Map]] that found for each <b>item</b> reference the updated [[Item]].
     * @param ground
     *   <b>Ground</b> implementation used in this <b>State</b>.
     * @param ended
     *   True if game is ended, false otherwise.
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
