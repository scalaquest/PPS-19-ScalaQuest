package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.InspectBag
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the InspectableBag behavior for the
 * Ground.
 */
trait InspectableBagExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt
  with CommonReactionsExt {

  /**
   * A [[GroundBehavior]] that enables the possibility to know the items present into the Bag.
   */
  abstract class InspectableBag extends GroundBehavior

  /**
   * A standard implementation for [[InspectableBag]].
   *
   * @param onInspectExtra
   *   [[Reaction]] to be executed when the player successfully inspected the bag. It can be
   *   omitted.
   */
  case class SimpleInspectableBag(onInspectExtra: Option[Reaction] = None) extends InspectableBag {

    override def triggers: GroundTriggers = { case (InspectBag, _) => inspectBag }

    private def inspectBag: Reaction =
      _.applyReactions(
        Reactions.inspectBag,
        onInspectExtra.getOrElse(Reactions.empty)
      )
  }

  /**
   * Companion object for [[InspectableBag]]. Shortcut for the standard implementation.
   */
  object InspectableBag {

    def apply(onInspectExtra: Option[Reaction] = None): InspectableBag =
      SimpleInspectableBag(onInspectExtra)
  }

}
