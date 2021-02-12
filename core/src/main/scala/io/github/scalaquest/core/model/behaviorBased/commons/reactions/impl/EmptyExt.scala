package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel

trait EmptyExt extends BehaviorBasedModel {
  private[reactions] def empty: Reaction = state => state
}
