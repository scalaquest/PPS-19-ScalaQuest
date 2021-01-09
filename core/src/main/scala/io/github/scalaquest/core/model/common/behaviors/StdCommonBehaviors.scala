package io.github.scalaquest.core.model.common.behaviors

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.behaviors.std.{Openable, RoomLink, Takeable}
import io.github.scalaquest.core.model.common.items.CommonItems
import io.github.scalaquest.core.model.std.BehaviorableModel
import monocle.Lens

/**
 * This is a mixable part of the model, that adds some implemented common behaviors to the model.
 */
trait StdCommonBehaviors
  extends StdCommonBehaviorsBase
  with CommonBehaviors
  with CommonItems
  with Takeable
  with Openable
  with RoomLink
