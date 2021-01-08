package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.default.BehaviorableModel

trait CommonBehaviors extends BehaviorableModel {

  object CommonBehaviors {
    trait Takeable extends Behavior
    trait Openable extends Behavior
    trait RoomLink extends Behavior
  }
}
