package io.github.scalaquest.core.model.std

import io.github.scalaquest.core.model.{Action, Model}

/**
 * This is a partial implementation of Model, based on the concept of Behaviors: every Item, can in
 * this model owe one or more Behavior, that are a sort of plugin system used for binding specific
 * action to an item.
 */
abstract class BehaviorableModel extends Model {
  override type I = BehaviorableItem

  /**
   * An item capable to process Behaviors to react to specific actions.
   *
   * Items that exploit the behavioral mechanism should extend this type of Item. This can handle
   * use* functions by exploring the available triggers, obtained by the item's Behaviors.
   */
  abstract class BehaviorableItem extends Item {
    def behaviors: Seq[Behavior] = Seq()

    override def use(action: Action, state: S, maybeSideItem: Option[I]): Option[Reaction] =
      behaviors.map(_.triggers).reduce(_ orElse _).lift((action, this, maybeSideItem, state))
  }

  /*
   * The behavioral model is based into the trigger construct. A 'Triggers' type is a partial function, that
   * makes possible to react to specific combinations action-items-state, responding with a reaction. All triggers
   * associated to an Item are combined in a single match case, every time a combination is launched for a given
   * item.
   */
  type Triggers = PartialFunction[(Action, I, Option[I], S), Reaction]

  /**
   * Makes an item subject of specific reaction, when some action occurs (eventually with a side
   * item). The only requirement is to expose some triggers, as a partial function.
   */
  abstract class Behavior {
    def triggers: Triggers = PartialFunction.empty
  }

  /**
   * A subtype of Behavior, that adds the possibility to compose multiple behaviors into a sigle
   * one, using a hierarchical fashion (father-son behavior).
   */
  trait Composable extends Behavior {

    /**
     * The father behavior triggers.
     * @return
     *   The father behavior triggers.
     */
    def superTriggers: Triggers

    /**
     * This should be overrode with all the triggers originating from the child behavior. Those will
     * be combined with the father triggers. Priority will be given to the base triggers, as could
     * overwrite some of the triggers of the father; this is a wanted feature.
     * @return
     *   The triggers of the child behavior.
     */
    def baseTriggers: Triggers = PartialFunction.empty

    /**
     * This function returns all the trigger of the composed behavior, by combining them in a single
     * partial function. Priority will be given to the base triggers, as could overwrite some of the
     * triggers of the father; this is a wanted feature.
     * @return
     *   The combined triggers.
     */
    final override def triggers: Triggers = Seq(baseTriggers, superTriggers).reduce(_ orElse _)
  }
}
