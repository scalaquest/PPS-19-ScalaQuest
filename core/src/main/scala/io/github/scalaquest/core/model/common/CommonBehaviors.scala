package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.default.BehaviorableModel

/**
 * This is a mixable part of the model, that adds some traits associated to common
 * behaviors to the base model hierarchy. These should be implemented to be used.
 */
trait CommonBehaviors extends BehaviorableModel {

  object CommonBehaviors {
    trait Takeable extends Behavior
    trait Openable extends Behavior
    trait RoomLink extends Behavior
  }
}
