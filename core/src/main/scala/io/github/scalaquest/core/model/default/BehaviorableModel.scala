package io.github.scalaquest.core.model.default

import io.github.scalaquest.core.model.{Action, Model}

abstract class BehaviorableModel extends Model {
  override type I = BehaviorableItem

  type TransitiveTriggers   = PartialFunction[(Action, I, S), Reaction]
  type DitransitiveTriggers = PartialFunction[(Action, I, I, S), Reaction]

  /**
   * A sort of plugin that can be added to an item to simulate a specific behaviors.
   */
  trait Behavior {
    def triggers: TransitiveTriggers               = PartialFunction.empty
    def ditransitiveTriggers: DitransitiveTriggers = PartialFunction.empty
  }

  trait ExtraUtils extends Behavior {
    protected def applyExtraIfPresent(extra: Option[Reaction])(state: S): S = extra.fold(state)(_(state))
  }

  trait ComposableBehavior extends Behavior {
    def superBehavior: Behavior
    def baseTrigger: TransitiveTriggers                = PartialFunction.empty
    def baseDitransitiveTriggers: DitransitiveTriggers = PartialFunction.empty

    override def triggers: TransitiveTriggers = Seq(baseTrigger, superBehavior.triggers).reduce(_ orElse _)

    override def ditransitiveTriggers: DitransitiveTriggers =
      Seq(baseDitransitiveTriggers, superBehavior.ditransitiveTriggers).reduce(_ orElse _)
  }

  trait BehaviorableItem extends Item {
    def behaviors: Set[Behavior] = Set()

    override def useTransitive(action: Action, state: S): Option[Reaction] =
      behaviors.map(_.triggers).reduce(_ orElse _).lift((action, this, state))

    override def useDitransitive(action: Action, state: S, sideItem: I): Option[Reaction] =
      behaviors.map(_.ditransitiveTriggers).reduce(_ orElse _).lift((action, this, sideItem, state))

  }

}
