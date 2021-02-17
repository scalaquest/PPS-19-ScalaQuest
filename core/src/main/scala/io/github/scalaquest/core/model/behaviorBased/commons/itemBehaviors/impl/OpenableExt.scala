package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Open
import io.github.scalaquest.core.model.behaviorBased.commons.items.impl.KeyExt
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into the [[BehaviorBasedModel]] the Openable behavior.
 */
trait OpenableExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with KeyExt
  with CommonMessagesExt
  with CommonReactionsExt {

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that can be opened a single time.
   */
  abstract class Openable extends ItemBehavior {
    def isOpen: Boolean
    def consumeKey: Boolean
    def requiredKey: Option[Key]
    def canBeOpened(usedKey: Option[I])(implicit state: S): Boolean
    def open: Reaction
  }

  /**
   * Standard implementation of the common Openable behavior.
   *
   * This is a behavior associated to an Item that can be opened a single time. Open The standard
   * [[Reaction]] consists into changing an internal variable that indicates the "openness" of the
   * Openable, when Statement "open Item (with something)" ais called.
   * @param requiredKey
   *   The key required to open the Item. If not passed the Item can be opened without Key.
   * @param onOpenExtra
   *   Reaction to be executed into the State when opened, after the standard Reaction. It can be
   *   omitted.
   */
  case class SimpleOpenable(
    requiredKey: Option[Key] = None,
    consumeKey: Boolean = false,
    onOpenExtra: Option[Reaction] = None
  )(implicit subject: I)
    extends Openable {
    var _isOpen: Boolean = false

    override def isOpen: Boolean = _isOpen

    override def triggers: ItemTriggers = {
      // "Open the item (with something)"
      case (Open, item, maybeKey, state)
          if state.isInLocation(item) && canBeOpened(maybeKey)(state) && !isOpen =>
        open

      case (Open, _, _, _) if !isOpen => failToOpen
      case (Open, _, _, _) if isOpen  => alreadyOpened
    }

    /**
     * Checks whether the Item can be opened with the current configuration. The usedKey is the
     * eventual Key passed explicitly by the user, with a statement like "open the item with the
     * key".
     *
     * @param state
     *   The current State.
     * @param usedKey
     *   The eventual key passed explicitly by the user, e.g. , with a statement like "open the item
     *   with the key".
     * @return
     *   True if:
     *   - A key was passed explicitly, is effectively into the Bag of the Player (or in the current
     *     room) and that key is effectively required to open the Item.
     *   - No key was passed explicitly, is effectively into the Bag of the Player and that key is
     *     effectively required to open the Item.
     *   - No key was passed explicitly, and no key is required.
     *
     * False otherwise.
     */
    override def canBeOpened(usedKey: Option[I] = None)(implicit state: S): Boolean = {
      usedKey match {
        case Some(key) => requiredKey.contains(key)
        case None      => requiredKey.fold(true)(_ => false)
      }
    }

    /**
     * Sets the Item in an open state and executes eventual extra actions.
     * @return
     *   A Reaction that sets the Item in an open state and executes eventual extra actions.
     */
    def open: Reaction =
      state => {
        _isOpen = true

        state.applyReactions(
          Reactions.open(subject, requiredKey, consumeKey),
          onOpenExtra.getOrElse(Reactions.empty)
        )
      }

    def failToOpen: Reaction =
      state => messageLens.modify(_ :+ Messages.FailedToOpen(subject))(state)

    def alreadyOpened: Reaction =
      state => messageLens.modify(_ :+ Messages.AlreadyOpened(subject))(state)
  }

  /**
   * Companion object for [[Openable]]. Shortcut for the standard implementation.
   */
  object Openable {

    def builder(
      requiredKey: Option[Key] = None,
      consumeKey: Boolean = true,
      onOpenExtra: Option[Reaction] = None
    ): I => Openable = SimpleOpenable(requiredKey, consumeKey, onOpenExtra)(_)
  }
}
