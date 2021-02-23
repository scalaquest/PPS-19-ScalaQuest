package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.Open
import io.github.scalaquest.core.model.behaviorBased.commons.items.impl.KeyExt
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to add into the [[BehaviorBasedModel]] the <b>Openable</b> behavior.
 */
trait OpenableExt
  extends BehaviorBasedModel
  with KeyExt
  with CMessagesExt
  with CReactionsExt
  with StateUtilsExt {

  /**
   * An <b>ItemBehavior</b> associated to a <b>BehaviorBasedItem</b> that can be opened a single
   * time.
   */
  abstract class Openable extends ItemBehavior {

    /**
     * The "openness" state of the subject, as a boolean value. Initially closed.
     * @return
     *   True if subject is already opened, False otherwise.
     */
    def isOpen: Boolean

    /**
     * The <b>Key</b> to be used to open the subject. If the subject can be opened without
     * <b>Key</b>, it can be set to [[None]].
     * @return
     *   [[Some]] with the <b>Key</b> if key is required, [[None]] otherwise.
     */
    def requiredKey: Option[Key]

    /**
     * Checks whether the subject can be opened with the current configuration.
     *
     * @param state
     *   The current <b>State</b>.
     * @param usedKey
     *   The eventual key passed explicitly by the user, e.g. , with a statement like "open the item
     *   with the key".
     * @return
     *   True if:
     *   - A <b>Key</b> was passed explicitly, is effectively into the bag of the player (or in the
     *     location) and that <b>Key</b> is effectively required to open the subject.
     *   - No <b>Key</b> was passed explicitly, is effectively into the bag of the player and that
     *     <b>Key</b> is effectively required to open the subject.
     *   - No <b>Key</b> was passed explicitly, and no <b>Key</b> is required.
     *
     * False otherwise.
     */
    def canBeOpened(usedKey: Option[I])(implicit state: S): Boolean

    /**
     * A <b>Reaction</b> that sets the subject in an open state.
     * @return
     *   A <b>Reaction</b> that sets the subject in an open state.
     */
    def open: Reaction
  }

  /**
   * Standard implementation of the <b>Openable</b> behavior.
   *
   * This is a behavior associated to an Item that can be opened a single time. The standard
   * <b>Reaction</b> consists into changing an internal variable that indicates the "openness" of
   * the subject, when <b>Statement</b> "open subject (with something)" is called.
   * @param requiredKey
   *   The <b>Key</b> required to open the Item. If not passed, the Item does not require a
   *   <b>Key</b> to be opened.
   * @param onOpenExtra
   *   <b>Reaction</b> to be executed into the State when opened, after the standard
   *   <b>Reaction</b>. It can be omitted.
   */
  case class SimpleOpenable(
    requiredKey: Option[Key] = None,
    onOpenExtra: Reaction = Reaction.empty
  )(implicit val subject: I)
    extends Openable {
    var _isOpen: Boolean = false

    override def isOpen: Boolean = _isOpen

    override def triggers: ItemTriggers = {
      case (Open, maybeKey, state)
          if state.isInLocation(subject) && canBeOpened(maybeKey)(state) && !isOpen =>
        open

      case (Open, _, _) =>
        if (!isOpen)
          Reaction.messages(CMessages.FailedToOpen(subject))
        else
          Reaction.messages(CMessages.AlreadyOpened(subject))
    }

    override def canBeOpened(usedKey: Option[I] = None)(implicit state: S): Boolean = {
      usedKey match {
        case Some(key) => requiredKey.contains(key)
        case None      => requiredKey.fold(true)(_ => false)
      }
    }

    override def open: Reaction = {
      _isOpen = true

      for {
        _ <-
          if (requiredKey.exists(_.disposable)) CReactions.modifyBag(_ - requiredKey.get.ref)
          else Reaction.empty
        _ <-
          if (requiredKey.exists(_.disposable))
            CReactions.modifyLocationItems(_ - requiredKey.get.ref)
          else Reaction.empty
        _ <- Reaction.messages(CMessages.Opened(subject))
        s <- onOpenExtra
      } yield s
    }
  }

  /**
   * Companion object for <b>Openable</b>.
   */
  object Openable {

    /**
     * Builder for an <b>Openable</b> that requires a specific <b>Key</b> to open the subject.
     * @param requiredKey
     *   The key required in order to open the subject.
     * @param onOpenExtra
     *   An extra <b>ItemBehavior</b> generated when the subject is opened. It can be omitted.
     * @return
     *   A builder for an <b>Openable</b> that requirea a specific <b>Key</b> to open the subject.
     */
    def lockedBuilder(
      requiredKey: Key,
      onOpenExtra: Reaction = Reaction.empty
    ): I => Openable = SimpleOpenable(Some(requiredKey), onOpenExtra)(_)

    /**
     * Builder for an <b>Openable</b> that not requires a specific <b>Key</b> to open the subject.
     * @param onOpenExtra
     *   An extra <b>ItemBehavior</b> generated when the subject is opened. It can be omitted.
     * @return
     *   A builder for an <b>Openable</b> that not requires a specific <b>Key</b> to open the
     *   subject.
     */
    def unlockedBuilder(
      onOpenExtra: Reaction = Reaction.empty
    ): I => Openable = SimpleOpenable(None, onOpenExtra)(_)
  }
}
