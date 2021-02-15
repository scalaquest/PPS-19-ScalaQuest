package io.github.scalaquest.core.model.behaviorBased.commons.reactions

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl._

trait CommonReactionsExt
  extends BehaviorBasedModel
  with EatExt
  with EnterExt
  with InspectLocationExt
  with FinishGameExt
  with NavigateExt
  with OpenExt
  with EmptyExt
  with TakeExt { self =>

  object Reactions {
    def eat(item: I): Reaction             = self.eat(item)
    def empty: Reaction                    = self.empty
    def enter(room: RM): Reaction          = self.enter(room)
    def take(item: I): Reaction            = self.take(item)
    def inspectLocation: Reaction          = self.inspectLocation
    def navigate(room: RM): Reaction       = self.navigate(room)
    def finishGame(win: Boolean): Reaction = self.finishGame(win)

    def open(
      itemToOpen: I,
      requiredKey: Option[Key],
      iskeyConsumable: Boolean
    ): Reaction = self.open(itemToOpen, requiredKey, iskeyConsumable)
  }
}
