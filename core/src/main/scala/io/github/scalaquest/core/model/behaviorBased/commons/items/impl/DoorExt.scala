package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.RoomLinkExt
import io.github.scalaquest.core.model.{Direction, ItemDescription, ItemRef}

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the <b>Door BehaviorBasedItem</b>.
 */
trait DoorExt extends BehaviorBasedModel with RoomLinkExt {

  /**
   * A <b>BehaviorBasedItem</b> that should work as a link between two different rooms.
   */
  trait Door extends BehaviorBasedItem {

    /**
     * The "openness" state of the item, as a boolean value. Initially closed.
     * @return
     *   True if item is already opened, False otherwise.
     */
    def isOpen: Boolean

    /**
     * A <b>Door</b> has always an associated <b>RoomLink</b> behavior, that can be referenced from
     * here.
     * @return
     *   The <b>RoomLink</b> behavior of the item.
     */
    def roomLink: RoomLink
  }

  /**
   * Standard implementation of <b>Door</b>.
   *
   * @param description
   *   An [[ItemDescription]] for the item.
   * @param ref
   *   A unique reference to the item.
   * @param roomLinkBuilder
   *   A builder for the <b>RoomLink</b> behavior associated to the item.
   * @param extraBehavBuilders
   *   Additional behaviors associated to the item.
   */
  case class SimpleDoor(
    description: ItemDescription,
    ref: ItemRef,
    roomLinkBuilder: I => RoomLink,
    extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
  ) extends Door {
    override val roomLink: RoomLink           = roomLinkBuilder(this)
    override val behaviors: Seq[ItemBehavior] = roomLink +: extraBehavBuilders.map(_(this))
    override def isOpen: Boolean              = roomLink.isOpen
  }

  /**
   * Companion object for <b>Door</b>.
   */
  object Door {

    /**
     * Creates a standard <b>Door</b>.
     * @param description
     *   An [[ItemDescription]] for the item.
     * @param roomLinkBuilder
     *   A builder for the <b>RoomLink</b> behavior associated to the item.
     * @param extraBehavBuilders
     *   Additional behaviors associated to the item.
     * @return
     *   An instance of a standard <b>Door</b>.
     */
    def apply(
      description: ItemDescription,
      roomLinkBuilder: I => RoomLink,
      extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
    ): Door = SimpleDoor(description, ItemRef(description), roomLinkBuilder, extraBehavBuilders)

    /**
     * Creates a Door-Key pair, in which the given <b>Key</b> can be used to open the associated
     * <b>Door</b>.
     *
     * @param key
     *   The <b>Key</b> to use to open the <b>Door</b>.
     * @param doorDesc
     *   An [[ItemDescription]] for the <b>Door</b>.
     * @param endRoom
     *   The <b>Room</b> that will be set as the new location of the player, after entering the
     *   <b>Door</b>.
     * @param endRoomDirection
     *   The [[Direction]] that will be associated to the end room, when available to enter.
     * @param onOpenExtra
     *   extra reaction generated when door is opened.
     * @param onEnterExtra
     *   An extra behavior generated when player enter the target room.
     * @param extraBehavBuilders
     *   An extra behavior generated when player opens the subject.
     * @return
     *   A tuple containing the generated <b>Door</b>, and the <b>Key</b> required to open it.
     */
    def createLockedWithKey(
      key: Key,
      doorDesc: ItemDescription,
      endRoom: RM,
      endRoomDirection: Direction,
      onOpenExtra: Reaction = Reaction.empty,
      onEnterExtra: Reaction = Reaction.empty,
      extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
    ): (Door, Key) = {
      val door = apply(
        description = doorDesc,
        RoomLink.closedLockedBuilder(
          endRoom = endRoom,
          endRoomDirection = endRoomDirection,
          key = key,
          onOpenExtra = onOpenExtra,
          onEnterExtra = onEnterExtra
        ),
        extraBehavBuilders = extraBehavBuilders
      )

      (door, key)
    }
  }
}
