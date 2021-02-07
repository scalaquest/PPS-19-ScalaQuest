package io.github.scalaquest.core.model.behaviorBased.commons.items

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.CommonItemBehaviorsExt

/**
 * When mixed into a [[Model]], it integrates into it the interfaces for some commonly used
 * [[BehaviorBasedModel.Item]] s. These should be implemented to be used.
 */
trait CommonItemsExt extends BehaviorBasedModel {

  /**
   * A [[BehaviorBasedItem]] that should work as a link between two different [[Room]] s.
   */
  trait Door extends BehaviorBasedItem {
    def isAccessible: Boolean
  }

  /**
   * A [[BehaviorBasedItem]] that should be used to open/close items with a
   * [[CommonItemBehaviorsExt.Openable]] behavior.
   */
  trait Key extends BehaviorBasedItem

  /**
   * A standard [[BehaviorBasedItem]], completely and freely configurable, without a specific
   * category.
   */
  trait GenericItem extends BehaviorBasedItem

  /**
   * A [[BehaviorBasedItem]] that have the [[CommonItemBehaviorsExt.Eatable]].
   */
  trait Food extends BehaviorBasedItem
}
