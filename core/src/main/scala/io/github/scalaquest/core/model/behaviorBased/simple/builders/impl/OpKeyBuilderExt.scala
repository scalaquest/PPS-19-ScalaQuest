package io.github.scalaquest.core.model.behaviorBased.simple.builders.impl

import io.github.scalaquest.core.model.ItemDescription
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.CommonsExt

trait OpKeyBuilderExt extends BehaviorBasedModel with CommonsExt {

  def openableWithKeyBuilder(
    keyDesc: ItemDescription,
    keyAddBehaviorsBuilders: Seq[I => ItemBehavior] = Seq.empty,
    consumeKey: Boolean = false,
    openableDesc: ItemDescription,
    onOpenExtra: Option[Reaction] = None,
    addOpenableItemBehaviorsBuilders: Seq[I => ItemBehavior] = Seq.empty
  ): (GenericItem, Key) = {

    val key = Key(keyDesc, keyAddBehaviorsBuilders)

    val openableItem = GenericItem(
      description = openableDesc,
      addBehaviorsBuilder = addOpenableItemBehaviorsBuilders :+ Openable.builder(
        consumeKey = consumeKey,
        requiredKey = Some(key),
        onOpenExtra = onOpenExtra
      )
    )

    (openableItem, key)
  }
}
