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

  val (livingRoomDoor, livingRoomKey): (SimpleModel.Door, SimpleModel.Key) = doorKeyBuilder(
    doorDesc = i(d("living-room"), "door"),
    keyDesc = i(d("living-room"), "key"),
    endRoom = livingRoom,
    onOpenExtra = Some(
      roomsLens.modify(
        _.updatedWith(kitchen.ref) {
          case Some(value) =>
            Some(roomDirectionsLens.modify(_ + (Direction.East -> livingRoom.ref))(value))
          case _ => None
        }
      )
    ),
    keyAddBehaviors = Seq(SimpleTakeable())
  )

  val kitchenDoor: SimpleDoor = {
    val itemDescription = i(d("kitchen-room"), "door")
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
