package io.github.scalaquest.core.model.common.behaviors

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.behaviors.impl.{Openable, RoomLink, Takeable}
import io.github.scalaquest.core.model.common.items.CommonItems
import io.github.scalaquest.core.model.default.BehaviorableModel
import monocle.Lens

/**
 * This is a mixable part of the model, that adds some implemented common behaviors to the model.
 */
trait DefaultCommonBehaviors
  extends DefaultCommonBehaviorsBase
  with CommonBehaviors
  with CommonItems
  with Takeable
  with Openable
  with RoomLink
