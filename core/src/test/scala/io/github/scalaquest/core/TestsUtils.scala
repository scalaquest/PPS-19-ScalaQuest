package io.github.scalaquest.core

import io.github.scalaquest.core.model.Action.Common.{Open, Take}
import io.github.scalaquest.core.model.Room.Direction
import io.github.scalaquest.core.model.{Action, ItemDescription, ItemRef, Room}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  BehaviorBasedItem,
  Door,
  GenericItem,
  Key,
  Openable,
  RoomLink,
  SimpleDoor,
  SimpleGenericItem,
  SimpleKey,
  SimpleMatchState,
  SimpleOpenable,
  SimplePlayer,
  SimpleRoomLink,
  SimpleState,
  SimpleTakeable
}
import io.github.scalaquest.core.model.DecoratedItem
import monocle.Lens
import monocle.macros.GenLens

object TestsUtils {
  val startRoom: Room = Room("startRoom", () => Map[Direction, Room](Direction.North -> targetRoom))

  val targetRoom: Room =
    Room("targetRoom", () => Map[Direction, Room](Direction.South -> startRoom))

  val actionsMap: Map[String, Action] = Map[String, Action](
    "take"  -> Take,
    "bring" -> Take,
    "open"  -> Open
  )

  val appleItemRef: ItemRef = new ItemRef {}
  val keyItemRef: ItemRef   = new ItemRef {}
  val doorItemRef: ItemRef  = new ItemRef {}

  val itemsMap: Map[ItemDescription, ItemRef] = Map[ItemDescription, ItemRef](
    ItemDescription("apple", "red") -> appleItemRef,
    ItemDescription("door")         -> doorItemRef,
    ItemDescription("key")          -> keyItemRef
  )

  val apple: GenericItem =
    SimpleGenericItem(ItemDescription("apple", "red"), appleItemRef, SimpleTakeable())
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
      player = SimplePlayer(bag = Set(), location = startRoom),
      ended = false,
      geography = Map(startRoom -> Set(), targetRoom -> Set()),
      Set()
    ),
    messages = Seq()
  )
}
