package io.github.scalaquest.core.model.default

import io.github.scalaquest.core.model.{Action, Model}

/**
 * This is a partial implementation of Model, based on the concept of Behaviors: every Item, can in this model owe
 * one or more Behavior, that are a sort of plugin system used for binding specific action to an item.
 */
abstract class BehaviorableModel extends Model {
  override type I = BehaviorableItem

  /**
   * An item capable to process Behaviors to react to specific actions.
   *
   * Items that exploit the behavioral mechanism should extend this type of Item. This can handle use* functions
   * by exploring the available triggers, obtained by the item's Behaviors.
   */
  abstract class BehaviorableItem extends Item {
    def behaviors: Set[Behavior] = Set()

    override def useTransitive(action: Action, state: S): Option[Reaction] =
      behaviors.map(_.triggers).reduce(_ orElse _).lift((action, this, None, state))

    override def useDitransitive(action: Action, state: S, sideItem: I): Option[Reaction] =
      behaviors.map(_.triggers).reduce(_ orElse _).lift((action, this, Some(sideItem), state))
  }

  /*
   * The behavioral model is based into the trigger construct. A 'Triggers' type is a partial function, that
   * makes possible to react to specific combinations action-items-state, responding with a reaction. All triggers
   * associated to an Item are combined in a single match case, every time a combination is launched for a given
   * item.
   */
  type Triggers = PartialFunction[(Action, I, Option[I], S), Reaction]

  /**
   * Makes an item subject of specific reaction, when some action occurs (eventually with a side item).
   * The only requirement is to expose some triggers, as a partial function.
   */
  abstract class Behavior {
    def triggers: Triggers = PartialFunction.empty
  }

  /**
   * A subtype of Behavior, that adds the possibility to compose multiple behaviors into a sigle one,
   * using a hierarchical fashion (father-son behavior).
   */
  trait Composable extends Behavior {

    /**
     * The father behavior, i.e. the one that can be seen by the child behavior.
     * @return The father behavior.
     */
    def superBehavior: Behavior

    /**
     * This should be overrode with all the triggers originating from the child behavior. Those will be
     * combined with the father triggers. Priority will be given to the base triggers, as could overwrite
     * some of the triggers of the father; this is a wanted feature.
     * @return The triggers of the child behavior.
     */
    def baseTrigger: Triggers = PartialFunction.empty

    /**
     * This function returns all the trigger of the composed behavior, by combining them in a single
     * partial function. Priority will be given to the base triggers, as could overwrite some of the
     * triggers of the father; this is a wanted feature.
     * @return The combined triggers.
     */
    final override def triggers: Triggers = Seq(baseTrigger, superBehavior.triggers).reduce(_ orElse _)
  }

  /**
   * A mixin for the Behavior, that adds to it some utilities to handle extra reactions, i.e. additional
   * reactions to be combined to the default ones.
   */
  trait ExtraUtils extends Behavior {
    protected def applyExtraIfPresent(extra: Option[Reaction])(state: S): S = extra.fold(state)(_(state))
  }
}
