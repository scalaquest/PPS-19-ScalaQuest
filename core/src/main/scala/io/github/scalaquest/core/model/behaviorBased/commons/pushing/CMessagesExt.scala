/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.commons.pushing

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.{Direction, Message}

/**
 * A mixable trait that adds to the [[BehaviorBasedModel]] the [[Message]] s used into common
 * implementations.
 */
trait CMessagesExt extends BehaviorBasedModel {

  /**
   * Object with some common messages.
   */
  object CMessages {

    /**
     * A [[Message]] generated when the player starts the match.
     * @param msg
     *   the message content.
     */
    case class Welcome(msg: String) extends Message

    /**
     * A [[Message]] generated when the user inspects the room.
     * @param room
     *   The current room.
     * @param items
     *   Items in the current room.
     */
    case class Inspected(room: RM, items: Set[I], neighbors: Map[Direction, RM]) extends Message

    /**
     * A [[Message]] generated when the user inspects the bag.
     * @param items
     *   The items contained in the bag.
     */
    case class InspectedBag(items: Set[I]) extends Message

    /**
     * A [[Message]] generated when the user navigate into a new the room.
     * @param room
     *   The new current position of the player, as a [[BehaviorBasedModel.Room]].
     */
    case class Navigated(room: RM) extends Message

    /**
     * A [[Message]] generated when the user's attempt to navigate in a non-navigable direction.
     * allow it.
     * @param direction
     *   The closed direction.
     */
    case class FailedToNavigate(direction: Direction) extends Message

    /**
     * A [[Message]] generated when the user's attempt to enter into an object that does not allow.
     * it.
     * @param item
     *   The blocked item.
     */
    case class FailedToEnter(item: I) extends Message

    /**
     * A [[Message]] generated when the user wants to know its current position, and the neighbor
     * rooms of the current one.
     * @param room
     *   The current room of the player.
     * @param neighbors
     *   The rooms directly linked and accessible from the current, via cardinal points.
     */
    case class Oriented(room: RM, neighbors: Map[Direction, RM]) extends Message

    /**
     * A [[Message]] generated when the user eats an item.
     * @param item
     *   The eaten item.
     */
    case class Eaten(item: I) extends Message

    /**
     * A [[Message]] generated when the user opens an item.
     * @param item
     *   The opened item.
     */
    case class Opened(item: I) extends Message

    /**
     * A [[Message]] generated when the user opens an item that contains some hidden items.
     * @param items
     *   the hidden items.
     */
    case class ReversedIntoLocation(items: Set[I]) extends Message

    /**
     * A [[Message]] generated when the user's attempt to open a non-opening object.
     * @param item
     *   The closed item.
     */
    case class FailedToOpen(item: I) extends Message

    /**
     * A [[Message]] generated when the user's attempt to open an already open item.
     * @param item
     *   The already opened item.
     */
    case class AlreadyOpened(item: I) extends Message

    /**
     * A [[ItemBehavior]] associated to an [[Item]] that can be taken a single time.
     */
    case class Taken(item: I) extends Message

    /**
     * A [[Message]] generated when player win the game.
     */
    case object Won extends Message

    /**
     * A [[Message]] generated when player lose the game.
     */
    case object Lost extends Message

    /**
     * A general [[Message]] that print a [[msg]] something in console.
     * @param msg
     *   the printed message.
     */
    case class Print(msg: String) extends Message
  }
}
