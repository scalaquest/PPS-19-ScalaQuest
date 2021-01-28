package io.github.scalaquest.core.model.behaviorBased.common

import io.github.scalaquest.core.model.behaviorBased.common.groundBehaviors.CommonGroundBehaviors
import io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.CommonBehaviors
import io.github.scalaquest.core.model.behaviorBased.common.items.CommonItems
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.common.pushing.CommonMessages
import io.github.scalaquest.core.model.impl.SimpleUtils

/**
 * A base trait used to implement all the StdCommon* mixins. Integrates some additional
 * functionalities for state inspection and re-generation, by the use of [[monocle.Lens]].
 */
trait CommonBase
  extends BehaviorBasedModel
  with SimpleUtils
  with CommonMessages
  with CommonGroundBehaviors
  with CommonItems
  with CommonBehaviors
