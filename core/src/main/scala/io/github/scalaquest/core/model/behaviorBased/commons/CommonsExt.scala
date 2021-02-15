package io.github.scalaquest.core.model.behaviorBased.commons

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.CommonGroundBehaviorsExt
import io.github.scalaquest.core.model.behaviorBased.commons.grounds.CommonGroundExt
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.CommonItemBehaviorsExt
import io.github.scalaquest.core.model.behaviorBased.commons.items.CommonItemsExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

trait CommonsExt
  extends BehaviorBasedModel
  with CommonItemBehaviorsExt
  with CommonItemsExt
  with CommonGroundBehaviorsExt
  with CommonGroundExt
  with CommonReactionsExt
