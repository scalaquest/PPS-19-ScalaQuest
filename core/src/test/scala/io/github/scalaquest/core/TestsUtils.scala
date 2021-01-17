package io.github.scalaquest.core

import io.github.scalaquest.core.model.Direction.Direction
import io.github.scalaquest.core.model.common.Actions
import io.github.scalaquest.core.model.{Action, Room}
import io.github.scalaquest.core.model.std.StdModel.{
  BehaviorableItem,
  Takeable,
  Door,
  GenericItem,
  Key,
  Openable,
  RoomLink,
  StdGameState,
  StdPlayer,
  StdState
}
import io.github.scalaquest.core.pipeline.interpreter.ItemRef
import monocle.Lens
import monocle.macros.GenLens

object TestsUtils {
  val startRoom: Room = Room("startRoom", () => Map[Direction, Room]())

  val simpleState: StdState = StdState(
    game = StdGameState(
      player = StdPlayer(bag = Set(), location = startRoom),
      ended = false,
      rooms = Set(startRoom),
      itemsInRooms = Map(startRoom -> Set())
    ),
    messages = Seq()
  )

  val actionsMap: Map[String, Action] = Map[String, Action](
    "take"  -> Actions.Take,
    "bring" -> Actions.Take,
    "open"  -> Actions.Open
  )

  val roomsLens: Lens[StdState, Set[Room]] = GenLens[StdState](_.game.rooms)

  val appleItemRef: ItemRef = new ItemRef {}
  val keyItemRef: ItemRef   = new ItemRef {}
  val doorItemRef: ItemRef  = new ItemRef {}

  val itemsMap: Map[String, ItemRef] = Map[String, ItemRef](
    "apple"     -> appleItemRef,
    "red apple" -> appleItemRef,
    "door"      -> doorItemRef,
    "key"       -> keyItemRef
  )

  val takeableApple: GenericItem = GenericItem("Apple", Takeable())
  val key: Key                   = Key("Key")

  val roomLinkDoor: Door =
    Door("Door", RoomLink(startRoom, Some(Openable(requiredKey = Some(key)))))

  val refItemDictionary: Map[ItemRef, BehaviorableItem] = Map(
    appleItemRef -> takeableApple,
    keyItemRef   -> key,
    doorItemRef  -> roomLinkDoor
  )
}
