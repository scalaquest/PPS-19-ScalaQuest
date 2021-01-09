package io.github.scalaquest.core.model.common.items

import io.github.scalaquest.core.model.std.BehaviorableModel

/**
 * This is a mixable part of the model, that adds some traits associated to common
 * items to the base model hierarchy. These should be implemented to be used.
 */
trait CommonItems extends BehaviorableModel {

  object CommonItems {
    trait Door        extends BehaviorableItem
    trait Key         extends BehaviorableItem
    trait GenericItem extends BehaviorableItem
  }
}
