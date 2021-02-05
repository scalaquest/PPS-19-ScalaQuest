package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.model.{Direction, ItemRef}
import io.github.scalaquest.examples.escaperoom.House.{kitchen, livingRoom}
import io.github.scalaquest.examples.escaperoom.Messages.SuperStonksPowered

object Items {
  import model._

  val redApple: SimpleFood = {
    val itemDescription = i(d("red"), "apple")
    SimpleFood(
      itemDescription,
      ItemRef(itemDescription),
      SimpleEatable(onEatExtra =
        Option(localState => messageLens.modify(_ :+ SuperStonksPowered)(localState))
      )
    )
  }

  /*  val livingRoomKey: SimpleKey = {
    val itemDescription = i(d("livingroom"), "key")
    SimpleKey(itemDescription, ItemRef(itemDescription), SimpleTakeable())
  }

   val livingRoomDoor: SimpleDoor = {
    val itemDescription = i(d("livingroom"), "door")
    SimpleDoor(
      itemDescription,
      ItemRef(itemDescription),
      SimpleRoomLink(
        livingRoom,
        Some(
          SimpleOpenable(
            requiredKey = Some(livingRoomKey),
            onOpenExtra = Some(state => {
              val newKitchen =
                roomDirectionsLens.modify(_ + (Direction.East -> livingRoom.ref))(
                  state.roomFromRef(kitchen.ref).get
                )
              matchRoomsLens.modify(_ + (newKitchen.ref -> newKitchen))(state)
            })
          )
        )
      )
    )
  }*/

  val (livingRoomDoor, livingRoomKey): (SimpleModel.Door, SimpleModel.Key) = DoorWithKey(
    doorDescription = i(d("living-room"), "door"),
    keyDescription = i(d("living-room"), "key"),
    room = livingRoom,
    onOpenExtra = Some(state => {
      val newKitchen =
        roomDirectionsLens.modify(_ + (Direction.East -> livingRoom.ref))(
          state.roomFromRef(kitchen.ref).get
        )
      matchRoomsLens.modify(_ + (newKitchen.ref -> newKitchen))(state)
    }),
    keyAdditionalBehaviors = Seq(SimpleTakeable())
  )

  val kitchenDoor: SimpleDoor = {
    val itemDescription = i(d("kitchenroom"), "door")
    SimpleDoor(
      itemDescription,
      ItemRef(itemDescription),
      SimpleRoomLink(kitchen)
    )
  }

  val apple: SimpleFood = {
    val itemDescription = i("apple")
    SimpleFood(
      itemDescription,
      ItemRef(itemDescription),
      SimpleEatable(onEatExtra =
        Option(localState => messageLens.modify(_ :+ SuperStonksPowered)(localState))
      )
    )
  }

  val greenApple: SimpleFood = {

    val itemDescription = i(d("green"), "apple")
    SimpleFood(
      itemDescription,
      ItemRef(itemDescription),
      SimpleEatable(onEatExtra =
        Option(localState => messageLens.modify(_ :+ SuperStonksPowered)(localState))
      )
    )
  }
}
