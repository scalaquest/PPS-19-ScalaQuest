package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.InspectBag
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the InspectableBag behavior for the
 * Ground.
 */
trait InspectableBagExt extends BehaviorBasedModel with CommonMessagesExt with CommonReactionsExt {

  /**
   * A [[GroundBehavior]] that enables the possibility to know what items are contained into the
   * bag.
   */
  abstract class InspectableBag extends GroundBehavior {

    /**
     * A [[Reaction]] that communicates to the user the items contained into the bag.
     * @return
     *   A [[Reaction]] that communicates to the user the items contained into the bag.
     */
    def inspectBag: Reaction
  }

  /**
   * A standard implementation for [[InspectableBag]].
   *
   * @param onInspectExtra
   *   [[Reaction]] to be executed when the player successfully inspects the bag. It can be omitted.
   */
  case class SimpleInspectableBag(onInspectExtra: Reaction = Reaction.empty)
    extends InspectableBag {

    override def triggers: GroundTriggers = { case (InspectBag, _) => inspectBag }

    override def inspectBag: Reaction =
      for {
        s1 <- Reaction.empty
        _  <- Reaction.messages(Messages.InspectedBag(s1.bag))
        s2 <- onInspectExtra
      } yield s2
  }

  /**
   * Companion object for [[InspectableBag]].
   */
  object InspectableBag {

    /**
     * Shortcut for the standard implementation.
     * @param onInspectExtra
     *   an additional [[Reaction]] generated when user inspect the bag.
     * @return
     *   an instance of [[SimpleInspectableBag]].
     */
    def apply(onInspectExtra: Reaction = Reaction.empty): InspectableBag =
      SimpleInspectableBag(onInspectExtra)
  }
}
