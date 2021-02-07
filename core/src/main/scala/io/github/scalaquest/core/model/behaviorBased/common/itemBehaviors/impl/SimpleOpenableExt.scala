package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.{Close, Open}
import io.github.scalaquest.core.model.Message
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import monocle.Lens

/**
 * The trait makes possible to mix into the StdCommonBehaviors the standard implementation of
 * Openable.
 */
trait SimpleOpenableExt extends CommonBase {

  /**
   * Standard implementation of the common Openable behavior.
   *
   * This is a behavior associated to an Item that can be opened and closed. Open and close actions
   * are mutually exclusive. The standard Reactions consists into changing an internal variable that
   * indicates the "openness" of the Openable, when Statements "open Item (with something)", "close
   * Item (with something)" are called.
   * @param _isOpen
   *   The initial openness state of the behavior. Default to closed False.
   * @param requiredKey
   *   The key required to open the Item. If not passed the Item can be open without Key.
   * @param onOpenExtra
   *   Reaction to be executed into the State when opened, after the standard Reaction. It can be
   *   omitted.
   * @param onCloseExtra
   *   Reaction to be executed into the State when closed, after the standard Reaction. It can be
   *   omitted.
   */
  case class SimpleOpenable(
    var _isOpen: Boolean = false,
    requiredKey: Option[Key] = None,
    onOpenExtra: Option[Reaction] = None,
    onCloseExtra: Option[Reaction] = None
  ) extends Openable {

    override def isOpen: Boolean = _isOpen

    override def triggers: ItemTriggers = {
      // "Open the item (with something)"
      case (Open, item, maybeKey, state)
          if state.isInLocation(item) && canBeOpened(state, maybeKey) && !isOpen =>
        open(item)

      // "Close the item (with something)"
      case (Close, item, maybeKey, state)
          if state.isInLocation(item) && canBeOpened(state, maybeKey) && isOpen =>
        close(item)
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
        state.applyReactions(
          messageLens.modify(_ :+ Opened(item)),
          onOpenExtra.getOrElse(state => state)
        )
      }

    /**
     * Sets the Item in a closed state and executes eventual extra actions.
     * @return
     *   A Reaction that sets the Item in a closed state and executes eventual extra actions.
     */
    def close(item: I): Reaction =
      state => {
        _isOpen = false
        state.applyReactions(
          messageLens.modify(_ :+ Closed(item)),
          onCloseExtra.getOrElse(state => state)
        )
      }
  }
}
