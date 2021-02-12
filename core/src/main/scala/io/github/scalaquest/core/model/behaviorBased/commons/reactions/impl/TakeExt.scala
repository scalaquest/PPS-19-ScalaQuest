package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

trait TakeExt extends BehaviorBasedModel with StateUtilsExt with CommonMessagesExt {

  private[reactions] def take(item: I): Reaction =
    state => {
      val updLocation = roomItemsLens.modify(_ - item.ref)(state.location)

      state.applyReactions(
        roomsLens.modify(_ + (updLocation.ref -> updLocation)),
        bagLens.modify(_ + item.ref),
        messageLens.modify(_ :+ Taken(item))
      )
    }
}
