package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.RoomLinkExt
import io.github.scalaquest.core.model.{Direction, ItemDescription, ItemRef}

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the Door Item.
 */
trait DoorExt extends BehaviorBasedModel with RoomLinkExt {

  /**
   * A [[BehaviorBasedItem]] that should work as a link between two different [[Room]] s.
   */
  trait Door extends BehaviorBasedItem {

    /**
     * @return
     *   true if the door is open, false otherwise.
     */
    def isOpen: Boolean

    /**
     * @return
     *   the specific [[RoomLink]] behavior.
     */
    def roomLink: RoomLink
  }

  /**
   * Standard implementation of the common [[Door]].
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
   * Companion object for [[Door]]. Makes the initialization more succinct.
   */
  object Door {

    /**
     * Facilitates the creation of a [[SimpleDoor]].
     * @param description
     *   the door's [[ItemDescription]].
     * @param roomLinkBuilder
     *   the roomLink behavior builder.
     * @param extraBehavBuilder
     *   some possible extra behavior.
     * @return
     *   an instance of SimpleDoor.
     */
    def apply(
      description: ItemDescription,
      roomLinkBuilder: I => RoomLink,
      extraBehavBuilder: Seq[I => ItemBehavior] = Seq.empty
    ): Door = SimpleDoor(description, ItemRef(description), roomLinkBuilder, extraBehavBuilder)

    /**
     * Facilitates the creation for a [[SimpleDoor]] that could be opened only with the right
     * [[Key]].
     * @param key
     *   the specific key that could open the Door.
     * @param doorDesc
     *   the door's [[ItemDescription]].
     * @param endRoom
     *   the room on the other side of the door.
     * @param endRoomDirection
     *   the direction where endRoom is placed respect the current player's location.
     * @param onOpenExtra
     *   extra reaction generated when door is opened.
     * @param onEnterExtra
     *   extra reaction generated when door is crossed.
     * @param extraBehavBuilders
     *   extra behavior for the items.
     * @return
     *   an instance of SimpleDoor.
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
        extraBehavBuilder = extraBehavBuilders
      )

      (door, key)
    }
  }
}
