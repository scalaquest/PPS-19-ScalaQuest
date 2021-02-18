package io.github.scalaquest.core.model.behaviorBased.commons.builders.impl

import io.github.scalaquest.core.model.ItemDescription
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.items.CommonItemsExt

/**
 * A convenient implementation for an openble item, and if present, a key connected to it.
 */
trait OpKeyBuilderExt extends BehaviorBasedModel with CommonItemsExt {

  def openableBuilder(
    key: Key,
    openableDesc: ItemDescription,
    onOpenExtra: Reaction = Reaction.empty,
    extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
  ): (GenericItem, Key) = {

    val openableItem = GenericItem(
      description = openableDesc,
      extraBehavBuilders = extraBehavBuilders :+ Openable.lockedBuilder(
        requiredKey = key,
        onOpenExtra = onOpenExtra
      )
    )

    (openableItem, key)
  }
}
