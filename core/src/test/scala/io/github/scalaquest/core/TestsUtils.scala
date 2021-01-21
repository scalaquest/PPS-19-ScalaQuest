package io.github.scalaquest.core

import io.github.scalaquest.core.model.Action.Common.{Open, Take}
import io.github.scalaquest.core.model.Room.Direction
import io.github.scalaquest.core.model.{Action, ItemRef, Room}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  BehaviorBasedItem,
  Door,
  GenericItem,
  SimpleGenericItem,
  Key,
  Openable,
  SimpleOpenable,
  RoomLink,
  SimpleRoomLink,
  SimpleMatchState,
  SimplePlayer,
  SimpleDoor,
  SimpleState,
  SimpleTakeable,
  SimpleKey
}
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

  val itemsMap: Map[String, ItemRef] = Map[String, ItemRef](
    "apple"     -> appleItemRef,
    "red apple" -> appleItemRef,
    "door"      -> doorItemRef,
    "key"       -> keyItemRef
  )

  val apple: GenericItem = SimpleGenericItem(appleItemRef, SimpleTakeable())
  val key: Key           = SimpleKey(keyItemRef)

  val door: Door =
    SimpleDoor(
      doorItemRef,
      SimpleRoomLink(targetRoom, Some(SimpleOpenable(requiredKey = Some(key))))
    )

  val refItemDictionary: Map[ItemRef, BehaviorBasedItem] = Map(
    appleItemRef -> apple,
    keyItemRef   -> key,
    doorItemRef  -> door
  )

  val simpleState: SimpleState = SimpleState(
    matchState = SimpleMatchState(
      player = SimplePlayer(bag = Set(), location = startRoom),
      ended = false,
      geography = Map(startRoom -> Set(), targetRoom -> Set()),
      Set()
    ),
    messages = Seq()
  )
}
