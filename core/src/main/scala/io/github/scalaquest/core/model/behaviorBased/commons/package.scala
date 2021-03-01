package io.github.scalaquest.core.model.behaviorBased

/**
 * Contains contains a set of commonly used <b>BehaviorBasedItems</b>, <b>ItemBehaviors</b>,
 * <b>GroundBehaviors</b>, <b>Reactions</b>, <b>Messages</b>, <b>Actions</b>, accessible with a
 * mixin mechanism from the [[BehaviorBasedModel]].
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
