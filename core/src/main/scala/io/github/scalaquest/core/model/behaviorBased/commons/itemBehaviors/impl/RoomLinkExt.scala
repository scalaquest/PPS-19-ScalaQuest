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

    /**
     * Check if RoomLink is accessible or not.
     * @return
     *   true if RoomLink is ready to use, false otherwise.
     */
    def isOpen: Boolean

    /**
     * RoomLink could have also the [[Openable]] Behavior.
     * @return
     *   [[Some]] of [[Openable]] if is present, [[None]] otherwise.
     */
    def openable: Option[Openable]

    /**
     * The room that would be reached using RoomLink.
     * @param s
     *   the current state.
     * @return
     *   the room that would be reached using RoomLink.
     */
    def endRoom(implicit s: S): RM

    /**
     * [[Reaction]] that could be used with RoomLink. RoomLink have to redefine the openable
     * behavior if is present.
     * @return
     *   the specific opened Reaction cited above.
     */
    def open: Reaction

    /**
     * [[Reaction]] generated when RoomLink is used.
     * @return
     *   the specific entered Reaction cited above.
     */
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
  )(implicit val subject: I)
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
      case (Open, maybeKey, s)
          if s.isInLocation(subject) && openable.forall(_.canBeOpened(maybeKey)(s))
            && !isOpen && openable.isDefined =>
        open

      case (Enter, None, s) if s.isInLocation(subject) && openable.forall(_.isOpen) =>
        enter

      case (Enter, _, _) =>
        Reaction.messages(Messages.FailedToEnter(subject))
    }

    override def enter: Reaction =
      for {
        s1 <- Reaction.empty
        _  <- Reactions.modifyLocation(endRoom(s1))
        _  <- Reaction.messages(Messages.Navigated(endRoom(s1)))
        s2 <- onEnterExtra
      } yield s2

    override def open: Reaction =
      for {
        s1 <- openable map (_.open) getOrElse Reaction.empty
        s2 <- Reactions.addDirectionToLocation(endRoomDirection, endRoom(s1))
      } yield s2

    override def endRoom(implicit state: S): RM = state.rooms(endRoomRef)

    override def isOpen: Boolean = openable forall (_.isOpen)
  }

  /**
   * Companion object for [[RoomLink]]. Shortcut for the standard implementation.
   */
  object RoomLink {

    /**
     * Create a builder for generate the [[RoomLink]].
     * @param endRoom
     *   the room that have to be reached using the RoomLink.
     * @param endRoomDirection
     *   the direction of room that have to be reached using the RoomLink.
     * @param openableBuilder
     *   the openable builder used for generate openable behavior
     * @param onEnterExtra
     *   an extra behavior generated when player enter in the [[Room]].
     * @return
     *   a builder that passed an [[Item]] create the RoomLink.
     */
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

    /**
     * Create a builder for generate the [[RoomLink]] without openable behavior. This means that the
     * RoomLink is ready to use.
     * @param endRoom
     *   the room that have to be reached using the RoomLink.
     * @param endRoomDirection
     *   the direction of room that have to be reached using the RoomLink.
     * @param onEnterExtra
     *   an extra behavior generated when player enter in the [[Room]].
     * @return
     *   a builder that passed an [[Item]] create the RoomLink.
     */
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

    /**
     * Create a builder for generate the [[RoomLink]] with an openable behavior. This means that the
     * roomlink have to be opened before the use.
     * @param endRoom
     *   the room that have to be reached using the RoomLink.
     * @param endRoomDirection
     *   the direction of room that have to be reached using the RoomLink.
     * @param onEnterExtra
     *   an extra behavior generated when player enter in the [[Room]].
     * @param onOpenExtra
     *   an extra behavior generated when player open the [[Item]].
     * @return
     *   a builder that passed an [[Item]] create the RoomLink.
     */
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

    /**
     * Create a builder for generate the [[RoomLink]] with an openable behavior. This means that the
     * roomlink have to be opened, in this case with a [[Key]], before the use.
     * @param endRoom
     *   the room that have to be reached using the RoomLink.
     * @param endRoomDirection
     *   the direction of room that have to be reached using the RoomLink.
     * @param onEnterExtra
     *   an extra behavior generated when player enter in the [[Room]].
     * @param onOpenExtra
     *   an extra behavior generated when player open the [[Item]].
     * @return
     *   a builder that passed an [[Item]] create the RoomLink.
     */
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
