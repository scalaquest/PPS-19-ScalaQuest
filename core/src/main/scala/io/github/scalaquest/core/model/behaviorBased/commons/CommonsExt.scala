/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.commons

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.CGroundBehaviorsExt
import io.github.scalaquest.core.model.behaviorBased.commons.grounds.CGroundExt
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.CItemBehaviorsExt
import io.github.scalaquest.core.model.behaviorBased.commons.items.CItemsExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CReactionsExt

/**
 * A mixable trait that contains a set of commonly used <b>BehaviorBasedItems</b>,
 * <b>ItemBehaviors</b>, <b>GroundBehaviors</b>, <b>Reactions</b>, <b>Messages</b>, <b>Actions</b>,
 * accessible with a mixin mechanism from the [[BehaviorBasedModel]].
 */
trait CommonsExt
  extends BehaviorBasedModel
  with CItemBehaviorsExt
  with CItemsExt
  with CGroundBehaviorsExt
  with CGroundExt
  with CReactionsExt
