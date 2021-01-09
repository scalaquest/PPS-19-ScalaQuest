package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.model.common.Actions.{Close, Open}
import io.github.scalaquest.core.model.common.behaviors.StdCommonBehaviorsBase

trait Openable extends StdCommonBehaviorsBase {

  /**
   * The behavior of an object that can be opened and closed.
   */
  case class Openable(
    var _isOpen: Boolean = false,
    requiredKey: Option[CommonItems.Key] = None,
    onOpenExtra: Option[Reaction] = None,
    onCloseExtra: Option[Reaction] = None
  ) extends CommonBehaviors.Openable
    with ExtraUtils {

    override def isOpen: Boolean = _isOpen

    override def triggers: Triggers = {
      case (Open, item, None, state) if state.isInCurrentRoom(item) && canBeOpened(state) && !isOpen =>
        open()
      case (Open, item, Some(key), state) if state.isInCurrentRoom(item) && canBeOpened(state, Some(key)) && !isOpen =>
        open()
      case (Close, item, None, state) if state.isInCurrentRoom(item) && canBeOpened(state) && isOpen =>
        close()
    }

    def canBeOpened(state: S, usedKey: Option[I] = None): Boolean = {
      usedKey match {
        case Some(key) => requiredKey.contains(key)
        case None      => requiredKey.fold(true)(state.isInBag(_))
      }
    }

    /**
     * Opens the item and executes eventual extra actions.
     */
    def open(): Reaction = state => { _isOpen = true; applyExtraIfPresent(onOpenExtra)(state) }

    /**
     * Closes the item and executes eventual extra actions.
     */
    def close(): Reaction = state => { _isOpen = false; applyExtraIfPresent(onCloseExtra)(state) }
  }

}
