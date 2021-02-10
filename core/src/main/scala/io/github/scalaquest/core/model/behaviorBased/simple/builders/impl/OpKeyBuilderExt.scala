package io.github.scalaquest.core.model.behaviorBased.simple.builders.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.CommonsExt
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

trait OpKeyBuilderExt extends BehaviorBasedModel with CommonsExt {

  def openableWithKeyBuilder(
    keyDesc: ItemDescription,
    keyAddBehaviors: Seq[ItemBehavior] = Seq.empty,
    consumeKey: Boolean = false,
    openableDesc: ItemDescription,
    onOpenExtra: Option[Reaction] = None,
    addOpenableItemBehaviors: Seq[ItemBehavior] = Seq.empty
  ): (GenericItem, Key) = {

    val key = Key(keyDesc, keyAddBehaviors)

    val openableItem = GenericItem(
      description = openableDesc,
      additionalBehaviors = addOpenableItemBehaviors :+ Openable(
        consumeKey = consumeKey,
        requiredKey = Some(key),
        onOpenExtra = onOpenExtra
      )
    )

    (openableItem, key)
  }
}
