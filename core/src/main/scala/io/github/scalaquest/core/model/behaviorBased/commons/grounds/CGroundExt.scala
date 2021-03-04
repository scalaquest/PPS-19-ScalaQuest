package io.github.scalaquest.core.model.behaviorBased.commons.grounds

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.CGroundBehaviorsExt

/**
 * Extension for the model, based on the behavior-based fashion. Adds a base implementation of the
 * <b>BehaviorBasedGround</b> with common behaviors inside, extensible on demand with more
 * behaviors.
 */
trait CGroundExt extends BehaviorBasedModel with CGroundBehaviorsExt {

  /**
   * A <b>BehaviorBasedGround</b> implementation that includes all the common
   * <b>GroundBehaviors</b>.
   * @param extraBehaviors
   *   <b>GroundBehaviors</b> to include in addition to the common ones.
   */
  case class CGround(extraBehaviors: Seq[GroundBehavior] = Seq.empty) extends BehaviorBasedGround {

    override val behaviors: Seq[GroundBehavior] =
      extraBehaviors ++ Seq(InspectableLocation(), InspectableBag(), Navigable())
  }

  /**
   * Companion object for <b>CGround</b>.
   */
  object CGround {

    /**
     * A <b>BehaviorBasedGround</b> with the common <b>GroundBehaviors</b>, and an extra custom
     * additional behavior.
     * @param extraBehavior
     *   The single additional behavior associated to the Ground.
     * @return
     *   A <b>BehaviorBasedGround</b> with the common <b>GroundBehaviors</b>, and an extra custom
     *   additional behavior.
     */
    def withSingleExtraBehavior(
      extraBehavior: GroundBehavior
    ): BehaviorBasedGround = CGround(Seq(extraBehavior))

    /**
     * A <b>BehaviorBasedGround</b> with the common <b>GroundBehaviors</b>, and an extra custom
     * additional behavior, created on-the-fly by some <b>GroundTriggers</b>.
     * @param behaviorTriggers
     *   Triggers for the single behavior associated to the item.
     * @return
     *   An instance of a <b>GenericItem</b> with a single behavior.
     */
    def withGenExtraBehavior(
      behaviorTriggers: GroundTriggers
    ): BehaviorBasedGround = CGround(Seq(GenericGroundBehavior(behaviorTriggers)))
  }
}
