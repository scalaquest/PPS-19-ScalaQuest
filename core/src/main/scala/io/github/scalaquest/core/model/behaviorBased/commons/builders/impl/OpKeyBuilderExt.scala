package io.github.scalaquest.core.model.behaviorBased.commons.builders.impl

import io.github.scalaquest.core.model.ItemDescription
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.items.CommonItemsExt

/**
 * A convenient implementation for an openble item, and if present, a key connected to it.
 */
trait OpKeyBuilderExt extends BehaviorBasedModel with CommonItemsExt {

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
