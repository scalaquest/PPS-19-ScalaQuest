package io.github.scalaquest.core.model.common.groundBehaviors

import io.github.scalaquest.core.model.std.BehaviorableModel

trait CommonGroundBehaviors extends BehaviorableModel {

  object CommonGroundBehaviors {
    abstract class Navigation extends GroundBehavior
    abstract class Inspect    extends GroundBehavior
  }
}
