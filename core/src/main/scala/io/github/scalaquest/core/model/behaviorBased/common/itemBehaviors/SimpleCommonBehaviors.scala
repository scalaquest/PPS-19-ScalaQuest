package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl.{
  SimpleEatableExt,
  SimpleOpenableExt,
  SimpleRoomLinkExt,
  SimpleTakeableExt
}

/**
 * When mixed into a [[Model]], it enables the implementation for the common behaviors provided by
 * ScalaQuest Core. It requires the storyteller to implement all the required [[monocle.Lens]], used
 * by the implementation to access and re-generate the concrete [[Model.State]].
 */
trait SimpleCommonBehaviors
  extends CommonBase
  with SimpleTakeableExt
  with SimpleEatableExt
  with SimpleOpenableExt
  with SimpleRoomLinkExt
