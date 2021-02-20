package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.{Direction, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.{Enter, Open}
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

/**
 * The trait makes possible to mix into [[BehaviorBasedModel]] the RoomLink behavior.
 */
trait RoomLinkExt extends BehaviorBasedModel with OpenableExt with CommonReactionsExt {

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that enables the possibility to move into another
   * [[Room]]. Conceptually, an [[Item]] that exposes a [[RoomLink]] behavior could also be
   * [[Openable]].
   */
  abstract class RoomLink extends ItemBehavior {
    def isOpen: Boolean
    def openable: Option[Openable]
    def endRoom(implicit s: S): RM
    def open: Reaction
    def enter: Reaction
  }

  /**
   * Standard implementation of the RoomLink.
   *
   * This is a behavior associated to an item that is a link between two rooms (a door, for
   * instance), and that could be opened to pass into it. The user can "Enter" into it, resulting
   * into move it into the connected Room. another room.
   * @param endRoomRef
   *   The room into which the user is projected after entering the object.
   * @param openable
   *   The Openable behavior associated to the roomLink. If not passed, the item can be entered
   *   without opening.
   * @param onEnterExtra
   *   Reaction to be executed into the State when entered, after the standard Reaction. It can be
   *   omitted.
   */
  case class SimpleRoomLink(
    endRoomRef: RoomRef,
    endRoomDirection: Direction,
    openable: Option[Openable] = None,
    onEnterExtra: Reaction = Reaction.empty
  )(implicit subject: I)
    extends RoomLink
    with Delegate {

    /**
     * If an openable is passed, it is passed as father behavior.
     * @return
     *   The father behavior triggers.
     */
    override def delegateTriggers: ItemTriggers =
      openable map (_.triggers) getOrElse PartialFunction.empty: ItemTriggers

    override def receiverTriggers: ItemTriggers = {
      case (Open, _, maybeKey, s)
          if s.isInLocation(subject) && openable.fold(true)(_.canBeOpened(maybeKey)(s))
            && !isOpen && openable.isDefined =>
        open

      case (Enter, _, None, s) if s.isInLocation(subject) && openable.fold(true)(_.isOpen) =>
        enter
      case (Enter, _, _, _) =>
        Reaction.messages(Messages.FailedToEnter(subject))
    }

    override def enter: Reaction =
      s =>
        Reaction.combine(
          Reactions.enter(endRoom(s)),
          onEnterExtra
        )(s)

    override def open: Reaction =
      Reaction.combine(
        openable map (_.open) getOrElse Reaction.empty,
        Reaction(
          (locationRoomLens composeLens roomDirectionsLens)
            .modify(_ + (endRoomDirection -> endRoomRef))
        )
      )

    override def endRoom(implicit s: S): RM = s.rooms(endRoomRef)

    override def isOpen: Boolean = openable forall (_.isOpen)
  }

  /**
   * Companion object for [[RoomLink]]. Shortcut for the standard implementation.
   */
  object RoomLink {

    def builder(
      endRoom: RM,
      endRoomDirection: Direction,
      openableBuilder: Option[I => Openable] = None,
      onEnterExtra: Reaction = Reaction.empty
    ): I => RoomLink =
      i =>
        SimpleRoomLink(
          endRoom.ref,
          endRoomDirection,
          openableBuilder.map(_(i)),
          onEnterExtra
        )(i)

    def openedBuilder(
      endRoom: RM,
      endRoomDirection: Direction,
      onEnterExtra: Reaction = Reaction.empty
    ): I => RoomLink =
      i =>
        SimpleRoomLink(
          endRoom.ref,
          endRoomDirection,
          None,
          onEnterExtra
        )(i)

    def closedUnlockedBuilder(
      endRoom: RM,
      endRoomDirection: Direction,
      onEnterExtra: Reaction = Reaction.empty,
      onOpenExtra: Reaction = Reaction.empty
    ): I => RoomLink =
      i =>
        SimpleRoomLink(
          endRoom.ref,
          endRoomDirection,
          Some(Openable.unlockedBuilder(onOpenExtra)(i)),
          onEnterExtra
        )(i)

    def closedLockedBuilder(
      key: Key,
      endRoom: RM,
      endRoomDirection: Direction,
      onEnterExtra: Reaction = Reaction.empty,
      onOpenExtra: Reaction = Reaction.empty
    ): I => RoomLink =
      i =>
        SimpleRoomLink(
          endRoom.ref,
          endRoomDirection,
          Some(Openable.lockedBuilder(key, onOpenExtra)(i)),
          onEnterExtra
        )(i)
  }
}
