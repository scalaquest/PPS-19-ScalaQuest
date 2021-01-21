package io.github.scalaquest.core.model.behaviorBased

/**
 * Contains a set of common [[BehaviorBasedModel.BehaviorBasedItem]],
 * [[BehaviorBasedModel.Behavior]], [[BehaviorBasedModel.GroundBehavior]] of common use, accessible
 * with a mixim mechanism from the [[BehaviorBasedModel]].
 *
 * Usage example:
 * {{{
 *   object MyModel extends BehaviorableModel
 *     with StdCommonBehaviors with StdCommonItems ...
 *
 *   import MyModel._
 *   val door = Door(itemRef, RoomLink(...))
 * }}}
 */
package object common {}
