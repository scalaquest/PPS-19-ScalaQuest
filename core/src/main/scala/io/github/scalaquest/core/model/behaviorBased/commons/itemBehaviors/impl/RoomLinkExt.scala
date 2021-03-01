package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.{Direction, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.{Enter, Open}
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CReactionsExt

/**
 * The trait makes possible to add into the [[BehaviorBasedModel]] the <b>RoomLink</b> behavior.
 */
trait RoomLinkExt extends BehaviorBasedModel with OpenableExt with CReactionsExt {

  /**
   * An <b>ItemBehavior</b> associated to a <b>BehaviorBasedItem</b> that enables the possibility to
   * change the player location when entered, like a <b>Door</b>. Conceptually, an subject that
   * exposes a <b>RoomLink</b> behavior could also be opened before permitting to the player to
   * enter.
   */
  abstract class RoomLink extends ItemBehavior {

    /**
     * The "openness" state of the subject, as a boolean value. An open subject is conceptually
     * "ready-to-use", as it can be entered. Always open, if the subject is not openable.
     * @return
     *   True if RoomLink is open, false otherwise.
     */
    def isOpen: Boolean

    /**
     * If the subject have to be opened before entering, the <b>RoomLink</b> should include [[Some]]
     * <b>Openable</b> behavior here. If the subject is set as always open, it can be set to
     * [[None]].
     * @return
     *   [[Some]] with an <b>Openable</b> behavior if the subject should be opened before entering,
     *   [[None]] otherwise.
     */
    def openable: Option[Openable]

    /**
     * The <b>Room</b> that will be set as the new location of the player, after entering the
     * subject.
     * @param s
     *   The current <b>State</b>.
     * @return
     *   The <b>Room</b> that will be set as the new location of the player, after entering the
     *   subject.
     */
    def endRoom(implicit s: S): RM

    /**
     * A <b>Reaction</b> that sets the subject in an open state. Internally, it should exploit the
     * <b>Openable::open</b> <b>Reaction</b>, if an <b>Openable</b> is passed.
     * @return
     *   A <b>Reaction</b> that sets the subject in an open state.
     */
    def open: Reaction

    /**
     * A <b>Reaction</b> that sets the new location of the player to <b>RoomLink::endRoom</b>, and
     * notifies the move to the user.
     * @return
     *   A <b>Reaction</b> that sets the new location of the player to <b>RoomLink::endRoom</b>, and
     *   notifies the move to the user.
     */
    def enter: Reaction
  }

  /**
   * Standard implementation of <b>RoomLink</b>.
   *
   * @param endRoomRef
   *   The [[RoomRef]] of the <b>Room</b> that will be set as the new location of the player, after
   *   entering the subject.
   * @param endRoomDirection
   *   The [[Direction]] that will be associated to the end room, when available to enter.
   * @param openable
   *   If the subject have to be opened before entering, the <b>RoomLink</b> should include [[Some]]
   *   <b>Openable</b> behavior here. If the subject is set as always open, it can be set to
   *   [[None]].
   * @param onEnterExtra
   *   <b>Reaction</b> to be executed into the <b>State</b> when entered, after the standard
   *   <b>Reaction</b>. It can be omitted.
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
     * If the subject can also be opened, che associated <b>Openable</b> triggers are used as a
     * delegate.
     * @return
     *   The <b>Openable</b> triggers, if the openable is passed; an empty [[PartialFunction]]
     *   otherwise.
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
        Reaction.messages(CMessages.FailedToEnter(subject))
    }

    override def enter: Reaction =
      for {
        s1 <- Reaction.empty
        _  <- CReactions.modifyLocation(endRoom(s1))
        _  <- Reaction.messages(CMessages.Navigated(endRoom(s1)))
        s2 <- onEnterExtra
      } yield s2

    override def open: Reaction =
      for {
        s1 <- openable map (_.open) getOrElse Reaction.empty
        s2 <- CReactions.addDirectionToLocation(endRoomDirection, endRoom(s1))
      } yield s2

    override def endRoom(implicit state: S): RM = state.rooms(endRoomRef)

    override def isOpen: Boolean = openable forall (_.isOpen)
  }

  /**
   * Companion object for <b>RoomLink</b>.
   */
  object RoomLink {

    /**
     * Builder to generate a simple <b>RoomLink</b>, given a subject.
     *
     * @param endRoom
     *   The <b>Room</b> that will be set as the new location of the player, after entering the
     *   subject.
     * @param endRoomDirection
     *   The [[Direction]] that will be associated to the end room, when available to enter.
     * @param openableBuilder
     *   A function that returns the <b>Openable</b> behavior associated to the subject, when the
     *   subject is passed.
     * @param onEnterExtra
     *   An extra behavior generated when player enter the target room.
     * @return
     *   Builder to generate a simple <b>RoomLink</b>, given a subject.
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
     * Builder to generate a <b>RoomLink</b> (given a subject) that is open yet, an can be entered
     * directly.
     *
     * @param endRoom
     *   The <b>Room</b> that will be set as the new location of the player, after entering the
     *   subject.
     * @param endRoomDirection
     *   The [[Direction]] that will be associated to the end room, when available to enter.
     * @param onEnterExtra
     *   An extra behavior generated when player enter the target room.
     * @return
     *   Builder to generate a simple <b>RoomLink</b>, given a subject.
     */
    def openedBuilder(
      endRoom: RM,
      endRoomDirection: Direction,
      onEnterExtra: Reaction = Reaction.empty
    ): I => RoomLink = builder(endRoom, endRoomDirection, None, onEnterExtra)

    /**
     * Builder to generate a <b>RoomLink</b> (given a subject) that is closed without <b>Key</b>,
     * and can be entered after being opened.
     *
     * @param endRoom
     *   The <b>Room</b> that will be set as the new location of the player, after entering the
     *   subject.
     * @param endRoomDirection
     *   The [[Direction]] that will be associated to the end room, when available to enter.
     * @param onEnterExtra
     *   An extra behavior generated when player enter the target room.
     * @param onOpenExtra
     *   An extra behavior generated when player opens the subject.
     * @return
     *   Builder to generate a simple <b>RoomLink</b>, given a subject.
     */
    def closedUnlockedBuilder(
      endRoom: RM,
      endRoomDirection: Direction,
      onEnterExtra: Reaction = Reaction.empty,
      onOpenExtra: Reaction = Reaction.empty
    ): I => RoomLink =
      builder(endRoom, endRoomDirection, Some(Openable.unlockedBuilder(onOpenExtra)), onEnterExtra)

    /**
     * Builder to generate a <b>RoomLink</b> (given a subject) that is closed with a <b>Key</b>, and
     * can be entered after being opened.
     *
     * @param key
     *   The <b>Key</b> to be used to open the subject.
     * @param endRoom
     *   The <b>Room</b> that will be set as the new location of the player, after entering the
     *   subject.
     * @param endRoomDirection
     *   The [[Direction]] that will be associated to the end room, when available to enter.
     * @param onEnterExtra
     *   An extra behavior generated when player enter the target room.
     * @param onOpenExtra
     *   An extra behavior generated when player opens the subject.
     * @return
     *   Builder to generate a simple <b>RoomLink</b>, given a subject.
     */
    def closedLockedBuilder(
      key: Key,
      endRoom: RM,
      endRoomDirection: Direction,
      onEnterExtra: Reaction = Reaction.empty,
      onOpenExtra: Reaction = Reaction.empty
    ): I => RoomLink =
      builder(
        endRoom,
        endRoomDirection,
        Some(Openable.lockedBuilder(key, onOpenExtra)),
        onEnterExtra
      )
  }
}
