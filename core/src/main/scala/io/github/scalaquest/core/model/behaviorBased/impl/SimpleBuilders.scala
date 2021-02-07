package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.dictionary.verbs.VerbPrep
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.SimpleCommonBehaviors
import io.github.scalaquest.core.model.behaviorBased.common.items.SimpleCommonItems
import io.github.scalaquest.core.model._

/**
 * Utilities to easily build various parts, for the storyteller. todo can be articulated even more
 */
trait SimpleBuilders
  extends BehaviorBasedModel
  with SimpleCommonBehaviors
  with SimpleCommonItems
  with SimpleState
  with SimpleGround
  with SimpleRoom {

  def roomFactory(
    name: String,
    neighbors: => Map[Direction, RoomRef],
    items: => Set[ItemRef]
  ): SimpleRoom = SimpleRoom(name, () => items, () => neighbors, RoomRef(name))

  def stateFactory(
    actions: Map[VerbPrep, Action],
    rooms: Map[RoomRef, RM],
    items: Map[ItemRef, I],
    ground: G = SimpleGround,
    bag: Set[ItemRef] = Set(),
    location: RoomRef,
    messages: Seq[Message] = Seq.empty,
    ended: Boolean = false
  ): SimpleState = SimpleState(actions, rooms, items, ground, bag, location, messages, ended)

  def doorKeyFactory(
    doorDescription: ItemDescription,
    keyDescription: ItemDescription,
    room: RM,
    onOpenExtra: Option[Reaction] = None,
    onEnterExtra: Option[Reaction] = None,
    doorAdditionalBehaviors: Seq[ItemBehavior] = Seq(),
    keyAdditionalBehaviors: Seq[ItemBehavior] = Seq()
  ): (Door, Key) = {

    val key: Key = SimpleKey(keyDescription, ItemRef(keyDescription), keyAdditionalBehaviors: _*)

    (
      SimpleDoor(
        doorDescription,
        ItemRef(doorDescription),
        SimpleRoomLink(
          room,
          Some(
            SimpleOpenable(
              requiredKey = Some(key),
              onOpenExtra = onOpenExtra
            )
          ),
          onEnterExtra
        ),
        doorAdditionalBehaviors: _*
      ),
      key
    )
  }
}
