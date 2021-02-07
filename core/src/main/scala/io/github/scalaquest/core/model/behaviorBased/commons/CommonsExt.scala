package io.github.scalaquest.core.model.behaviorBased.commons

import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.CommonSimpleGroundBehaviorsExt
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.CommonSimpleItemBehaviorsExt
import io.github.scalaquest.core.model.behaviorBased.commons.items.CommonSimpleItemsExt

trait CommonsExt
  extends CommonBase
  with CommonSimpleItemBehaviorsExt
  with CommonSimpleItemsExt
  with CommonSimpleGroundBehaviorsExt
