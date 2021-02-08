package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Open
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.items.impl.KeyExt
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into the [[BehaviorBasedModel]] the Openable behavior.
 */
trait OpenableExt extends BehaviorBasedModel with StateUtilsExt with KeyExt with CommonMessagesExt {

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that can be opened a single time.
   */
  abstract class Openable extends ItemBehavior {
    def isOpen: Boolean
    def consumeKey: Boolean
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
  ) extends Openable {
    var _isOpen: Boolean = false

    override def isOpen: Boolean = _isOpen

    override def triggers: ItemTriggers = {
      // "Open the item (with something)"
      case (Open, item, maybeKey, state)
          if state.isInLocation(item) && canBeOpened(state, maybeKey) && !isOpen =>
        open(item)
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
    def canBeOpened(state: S, usedKey: Option[I] = None): Boolean = {
      usedKey match {
        case Some(key) => requiredKey.contains(key) && state.isInScope(key)
        case None      => requiredKey.fold(true)(state.isInBag(_))
      }
    }

    /**
     * Sets the Item in an open state and executes eventual extra actions.
     * @return
     *   A Reaction that sets the Item in an open state and executes eventual extra actions.
     */
    def open(item: I): Reaction =
      state => {
        _isOpen = true

        val keyConsumedState: S = requiredKey.fold(state)(k => {
          val newLoc = roomItemsLens.modify(_ - k.ref)(state.location)
          state.applyReactions(
            roomsLens.modify(_ + (newLoc.ref -> newLoc)),
            bagLens.modify(_ - k.ref)
          )
        })

        keyConsumedState.applyReactions(
          messageLens.modify(_ :+ Opened(item)),
          onOpenExtra.getOrElse(state => state)
        )
      }
  }

  /**
   * Companion object for [[Openable]]. Shortcut for the standard implementation.
   */
  object Openable {

    def apply(
      requiredKey: Option[Key] = None,
      consumeKey: Boolean = false,
      onOpenExtra: Option[Reaction] = None
    ): Openable = SimpleOpenable(requiredKey, consumeKey, onOpenExtra)
  }
}
