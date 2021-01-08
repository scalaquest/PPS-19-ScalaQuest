package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.default.BehaviorableModel

trait CommonItems extends BehaviorableModel {

  object CommonItems {
    trait Door        extends BehaviorableItem
    trait Key         extends BehaviorableItem
    trait GenericItem extends BehaviorableItem
  }
}
