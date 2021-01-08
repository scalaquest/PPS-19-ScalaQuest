package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.Actions.{Close, Enter, Open, Take}
import io.github.scalaquest.core.model.default.BehaviorableModel
import monocle.Lens

/**
 * This is a mixable part of the model, that adds some implemented common behaviors to the model.
 */
trait DefaultCommonBehaviors extends BehaviorableModel with CommonBehaviors with CommonItems {

  /**
   * Adds some utilities to perform different checks into the state object.
   */
  implicit class StateUtils(state: S) {
    def isInBag(item: I): Boolean = state.game.player.bag.contains(item)

    def isInCurrentRoom(item: I): Boolean =
      state.game.itemsInRooms.collectFirst({ case (room, items) if items.contains(item) => room }).isDefined
  }

  /**
   * The behavior of an Item that could be put into the bag.
   * @param onTakeExtra a Reaction to be possibly chained to the basic take Reaction.
   */
  case class Takeable(onTakeExtra: Option[Reaction] = None)(implicit
    bagLens: Lens[S, Set[I]],
    itemsLens: Lens[S, Map[Room, Set[I]]]
  ) extends CommonBehaviors.Takeable
    with ExtraUtils {

    override def triggers: Triggers = {
      // controlla se l'oggetto è nella room
      case (Take, item, None, state) if state.isInCurrentRoom(item) => take(item)
    }

    // The standard take reaction is to remove the item from the current room, and put it into the bag.
    // executes also onTakeExtra, if available
    private def take(item: I): Reaction =
      state => {
        // remove the item from the current room
        val currRoom = state.game.player.location
        val currRoomItemsUpd =
          itemsLens.get(state).get(currRoom).fold(Set[I]())(crItems => crItems + item)
        val stateWithoutItem = itemsLens.modify(_ + (currRoom -> currRoomItemsUpd))(state)

        // put the item into the bag
        val stateItemInBag = bagLens.modify(_ + item)(stateWithoutItem)

        // execute additional reaction, if specified
        applyExtraIfPresent(onTakeExtra)(stateItemInBag)
      }
  }

  /**
   * The behavior of an object that can be opened and closed.
   */
  case class Openable(
    var isOpen: Boolean = false,
    requiredKey: Option[CommonItems.Key] = None,
    onOpenExtra: Option[Reaction] = None,
    onCloseExtra: Option[Reaction] = None
  ) extends CommonBehaviors.Openable
    with ExtraUtils {

    override def triggers: Triggers = {
      case (Open, item, None, state) if state.isInCurrentRoom(item) && canBeOpened(state) && !isOpen =>
        open()
      case (Open, item, Some(key), state) if state.isInCurrentRoom(item) && canBeOpened(state, Some(key)) && !isOpen =>
        open()
      case (Close, item, None, state) if state.isInCurrentRoom(item) && canBeOpened(state) && isOpen =>
        close()
    }

    def canBeOpened(state: S, usedKey: Option[I] = None): Boolean = {
      usedKey match {
        case Some(key) => requiredKey.contains(key)
        case None      => requiredKey.fold(true)(state.isInBag(_))
      }
    }

    /**
     * Opens the item and executes eventual extra actions.
     */
    def open(): Reaction = state => { isOpen = true; applyExtraIfPresent(onOpenExtra)(state) }

    /**
     * Closes the item and executes eventual extra actions.
     */
    def close(): Reaction = state => { isOpen = false; applyExtraIfPresent(onCloseExtra)(state) }
  }

  /**
   * The behavior of a door, for example: with transitive action Enter, it moves the player into
   * another room.
   */
  case class RoomLink(
    endRoom: Room,
    openable: Openable,
    onEnterExtra: Option[Reaction] = None
  )(
    implicit currRoomLens: Lens[S, Room]
  ) extends CommonBehaviors.RoomLink
    with Composable
    with ExtraUtils {

    override def superBehavior: Behavior = openable

    override def baseTrigger: Triggers = {
      case (Enter, _, None, _) if openable.isOpen => enterRoom()
    }

    def enterRoom(): Reaction =
      state => {
        val updLocState = currRoomLens.modify(_ => endRoom)(state)
        applyExtraIfPresent(onEnterExtra)(updLocState)
      }
  }
}
