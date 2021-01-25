package io.github.scalaquest.core

import io.github.scalaquest.core.model.Action.Common.{Open, Take}
import io.github.scalaquest.core.model.Room.Direction
import io.github.scalaquest.core.model.{Action, ItemDescription, ItemRef, Room}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  BehaviorBasedItem,
  Door,
  GenericItem,
  Key,
  SimpleDoor,
  SimpleGenericItem,
  SimpleKey,
  SimpleOpenable,
  SimpleRoomLink,
  SimpleState,
  SimpleTakeable,
  SimplePlayer,
  SimpleMatchState
}

object TestsUtils {

  val startRoom: Room =
    Room("startRoom", Map[Direction, Room](Direction.North -> targetRoom), Set(door, key))

  val targetRoom: Room =
    Room("targetRoom", Map[Direction, Room](Direction.South -> startRoom), Set())

  val actionsMap: Map[String, Action] = Map[String, Action](
    "take"  -> Take,
    "bring" -> Take,
    "open"  -> Open
  )

  val appleItemRef: ItemRef = ItemRef()
  val keyItemRef: ItemRef   = ItemRef()
  val doorItemRef: ItemRef  = ItemRef()

  val itemsMap: Map[ItemDescription, ItemRef] = Map[ItemDescription, ItemRef](
    ItemDescription("apple", "red") -> appleItemRef,
    ItemDescription("door")         -> doorItemRef,
    ItemDescription("key")          -> keyItemRef
  )

  val apple: GenericItem = SimpleGenericItem(
    ItemDescription("apple", "big", "red", "juicy"),
    appleItemRef,
    SimpleTakeable()
  )
  val key: Key = SimpleKey(ItemDescription("key"), keyItemRef)

  val door: Door =
    SimpleDoor(
      ItemDescription("door"),
      doorItemRef,
      SimpleRoomLink(targetRoom, Some(SimpleOpenable(requiredKey = Some(key))))
    )

  val refItemDictionary: Map[ItemRef, BehaviorBasedItem] = Map(
    appleItemRef -> apple,
    keyItemRef   -> key,
    doorItemRef  -> door
  )

  val simpleState: SimpleState = SimpleState(
    actionsMap,
    matchState = SimpleMatchState(
      player = SimplePlayer(bag = Set(appleItemRef), location = startRoom.id),
      ended = false,
      items = Set(apple, key, door),
      rooms = Set(startRoom, targetRoom)
    ),
    messages = Seq()
  )
}
