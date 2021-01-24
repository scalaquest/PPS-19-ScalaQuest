package io.github.scalaquest.core.model.behaviorBased

/**
 * Contains a set of common [[BehaviorBasedModel.BehaviorBasedItem]],
 * [[BehaviorBasedModel.ItemBehavior]], [[BehaviorBasedModel.GroundBehavior]] of common use,
 * accessible with a mixim mechanism from the [[BehaviorBasedModel]].
 *
 * Usage example:
 * {{{
 *   object MyModel extends BehaviorableModel
 *     with SimpleCommonBehaviors with SimpleCommonItems ...
 *
 *   import MyModel._
 *   val door = SimpleDoor(itemRef, SimpleRoomLink(...))
 * }}}
 */
package object common {}
