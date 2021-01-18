package io.github.scalaquest.core.model.common.items

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.std.BehaviorableModel
import io.github.scalaquest.core.model.common.behaviors.CommonBehaviors

/**
 * When mixed into a [[Model]], it integrates into it the interfaces for some commonly used
 * [[BehaviorableModel.Item]] s. These should be implemented to be used.
 */
trait CommonItems extends BehaviorableModel {

  /** Interfaces for commonly used [[BehaviorableModel.Item]]s. */
  object CommonItems {

    /**
     * A [[BehaviorableItem]] that should work as a link between two different [[Room]] s.
     */
    trait Door extends BehaviorableItem

    /**
     * A [[BehaviorableItem]] that should be used to open/close items with a
     * [[CommonBehaviors.CommonBehaviors.Openable]] behavior.
     */
    trait Key extends BehaviorableItem

    /**
     * A standard [[BehaviorableItem]], completely and freely configurable, without a specific
     * category.
     */
    trait GenericItem extends BehaviorableItem

    /**
     * A [[BehaviorableItem]] that have the [[CommonBehaviors.CommonBehaviors.Eatable]].
     */
    trait Food extends BehaviorableItem
  }
}
