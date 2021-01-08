package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.Actions.{Close, Enter, Open, Take}
import io.github.scalaquest.core.model.default.BehaviorableModel
import monocle.Lens

trait CommonBehaviors extends ItemCategories {

  private object StateUtils {
    def isInBag(item: I)(state: S): Boolean = state.game.player.bag.contains(item)

    def isInCurrentRoom(item: I)(state: S): Boolean =
      state.game.itemsInRooms.collectFirst({ case (room, items) if items.contains(item) => room }).isDefined
  }

  /**
   * The behavior of an Item that could be put into the bag.
   * @param onTakeExtra a Reaction to be possibly chained to the basic take Reaction.
   */
  case class Takeable(onTakeExtra: Option[Reaction] = None)(
    bagLens: Lens[S, Set[I]],
    itemsLens: Lens[S, Map[Room, Set[I]]]
  ) extends Behavior
    with ExtraUtils {

    override def triggers: TransitiveTriggers = {
      // controlla se l'oggetto è nella room
      case (Take, item, state) if StateUtils.isInCurrentRoom(item)(state) => take(item)
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
    needsKey: Option[Categories.Key] = None,
    onOpenExtra: Option[Reaction] = None,
    onCloseExtra: Option[Reaction] = None
  ) extends Behavior
    with ExtraUtils {

    override def triggers: TransitiveTriggers = {
      case (Open, item, state) if StateUtils.isInCurrentRoom(item)(state) && hasKeyOrNotNeeded(state) && !isOpen =>
        open()
      case (Close, item, state) if StateUtils.isInCurrentRoom(item)(state) && hasKeyOrNotNeeded(state) && isOpen =>
        close()
    }

    override def ditransitiveTriggers: DitransitiveTriggers = {
      case (Enter, _, _key, _) if needsKey.contains(_key) && !isOpen => open()
    }

    def hasKeyOrNotNeeded(state: S): Boolean = needsKey.fold(true)(StateUtils.isInBag(_)(state))

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
  case class RoomLink(endRoom: Room, openable: Openable, onEnterExtra: Option[Reaction] = None)(currRoomLens: Lens[
    S,
    Room
  ])
    extends ComposableBehavior
    with ExtraUtils {

    override def superBehavior: Behavior = openable

    override def baseTrigger: TransitiveTriggers = {
      case (Enter, _, _) if openable.isOpen => enterRoom()
    }

    def enterRoom(): Reaction =
      state => {
        val updLocState = currRoomLens.modify(_ => endRoom)(state)
        applyExtraIfPresent(onEnterExtra)(updLocState)
      }
  }
}
