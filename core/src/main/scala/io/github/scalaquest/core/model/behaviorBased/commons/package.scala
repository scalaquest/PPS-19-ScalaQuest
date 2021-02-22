package io.github.scalaquest.core.model.behaviorBased

/**
 * Contains a set of common BehaviorBasedItems, ItemBehaviors, GroundBehaviors, of common use, some
 * common Reactions generated by the Behaviours, some common Messages, a pusher for the String
 * implementation and a accessible point with a mixin mechanism from the [[BehaviorBasedModel]].
 *
 * Usage example:
 * {{{
 *   object MyModel extends BehaviorableModel with CommonsExt
 *
 *   import MyModel.Door
 *   val door = Door.createUnlocked(i(d("big"), "door"))
 * }}}
 */
package object commons {}
