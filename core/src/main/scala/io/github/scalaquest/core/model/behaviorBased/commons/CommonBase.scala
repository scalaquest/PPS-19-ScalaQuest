package io.github.scalaquest.core.model.behaviorBased.commons

import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.CommonGroundBehaviorsExt
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.CommonItemBehaviorsExt
import io.github.scalaquest.core.model.behaviorBased.commons.items.CommonItemsExt
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.impl.StateUtilsExt

/**
 * A base trait used to implement all the StdCommon* mixins. Integrates some additional
 * functionalities for state inspection and re-generation, by the use of [[monocle.Lens]].
 */
private[commons] trait CommonBase
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt
  with CommonGroundBehaviorsExt
  with CommonItemsExt
  with CommonItemBehaviorsExt
