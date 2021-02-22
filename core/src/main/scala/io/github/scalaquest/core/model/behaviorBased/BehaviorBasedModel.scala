package io.github.scalaquest.core.model.behaviorBased

import io.github.scalaquest.core.model.{Action, Model}

/**
 * This is a partial implementation of [[Model]], following a <b>behavior-based fashion</b>. This is
 * a style that enables the "decoration" of [[Model.Item]] s and the [[Model.Ground]] by the use of
 * the <b>Behavior</b>: a sort of composable plugin that matches dynamically various
 * action-item-state scenarios.
 */
abstract class BehaviorBasedModel extends Model {
  override type I = BehaviorBasedItem

  /**
   * A [[Item]] capable to process [[ItemBehavior]] s to react to specific action-item-state
   * combinations.
   *
   * Items that exploit the behavioral mechanism should extend this partial implementation. This
   * because the handling of use* functions following this style (by exploring the available
   * triggers) is implemented here.
   */
  abstract class BehaviorBasedItem extends Item {

    /**
     * [[ItemBehavior]] s associated to the Item.
     *
     * @return
     *   [[ItemBehavior]] s associated to the Item.
     */
    def behaviors: Seq[ItemBehavior] = Seq.empty

    override def use(action: Action, maybeSideItem: Option[I])(implicit
      state: S
    ): Option[Reaction] =
      behaviors
        .map(_.triggers)
        .reduceOption(_ orElse _)
        .getOrElse(PartialFunction.empty)
        .lift((action, this, maybeSideItem, state))
  }

  /**
   * A [[PartialFunction]], that makes possible to react to specific combinations
   * action-item-sideItem-state (or action-item-state) responding with a [[Reaction]]. The
   * [[ItemBehavior]] is based into this construct.
   */
  type ItemTriggers = PartialFunction[(Action, I, Option[I], S), Reaction]

  /**
   * Makes a [[BehaviorBasedItem]] react to specific [[ItemTriggers]] with a [[Reaction]]. The
   * [[BehaviorBasedModel]] is based into this construct.
   */
  abstract class ItemBehavior {

    /**
     * All the [[ItemTriggers]] of this [[ItemBehavior]].
     * @return
     *   the [[ItemTriggers]].
     */
    def triggers: ItemTriggers = PartialFunction.empty
  }

  /**
   * A mixin for [[ItemBehavior]], that enables the possibility implement the delegation pattern for
   * them. It means that the receiver behavior (the one that mixes in [[Delegate]] ) can owe
   * internally another one, inheriting its [[ItemTriggers]]. If the receiver overwrites some of the
   * delegate triggers, the receiver ones have the priority, and the delegate ones are not executed.
   */
  trait Delegate extends ItemBehavior {

    /**
     * Should be overrode with the delegate [[ItemBehavior]] 's [[ItemTriggers]].
     *
     * @return
     *   The delegate [[ItemBehavior]] 's [[ItemTriggers]].
     */
    def delegateTriggers: ItemTriggers

    /**
     * Should be overrode with the [[ItemTriggers]] of the receiver (the behavior that mixes in
     * [[Delegate]] ).
     *
     * @return
     *   The [[ItemTriggers]] of the receiver (the behavior that mixes in [[Delegate]] ).
     */
    def receiverTriggers: ItemTriggers

    /**
     * Composes together all the [[ItemTriggers]], both of the receiver and of the delegate. If the
     * receiver overwrites some of the delegate triggers, the receiver ones have the priority, and
     * the delegate ones are not executed.
     *
     * @return
     *   The combined triggers.
     */
    final override def triggers: ItemTriggers =
      Seq(receiverTriggers, delegateTriggers).reduce(_ orElse _)
  }

  override type G = BehaviorBasedGround

  /**
   * A [[Ground]] capable to process [[GroundBehavior]] s to react to specific action-state
   * combinations.
   *
   * A Ground that exploit the behavioral mechanism should extend this partial implementation. This
   * because the handling of use* functions following this style (by exploring the available
   * triggers) is implemented here.
   */
  abstract class BehaviorBasedGround extends Ground {

    /**
     * [[GroundBehavior]] s associated to the Ground.
     *
     * @return
     *   [[GroundBehavior]] s associated to the Ground.
     */
    def behaviors: Seq[GroundBehavior] = Seq.empty

    override def use(action: Action)(implicit state: S): Option[Reaction] =
      behaviors
        .map(_.triggers)
        .reduceOption(_ orElse _)
        .getOrElse(PartialFunction.empty)
        .lift((action, state))
  }

  /**
   * A [[PartialFunction]], that makes possible to react to specific combinations action-state,
   * responding with a [[Reaction]]. The [[GroundBehavior]] is based into this construct.
   */
  type GroundTriggers = PartialFunction[(Action, S), Reaction]

  /**
   * Makes a [[BehaviorBasedGround]] react to specific [[GroundTriggers]] with a [[Reaction]]. The
   * [[BehaviorBasedModel]] is based into this construct.
   */
  abstract class GroundBehavior {

    /**
     * [[GroundTriggers]] s associated to the Ground.
     *
     * @return
     *   [[GroundTriggers]] s associated to the Ground.
     */
    def triggers: GroundTriggers = PartialFunction.empty
  }
}
