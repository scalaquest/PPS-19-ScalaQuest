package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.examples.escaperoom.House.{kitchen, livingRoom}
import io.github.scalaquest.examples.escaperoom.Messages.SuperStonksPowered

object Items {
  import model._

  val redApple: Food = {
    Food(
      i(d("red"), "apple"),
      SimpleEatable(onEatExtra = Some(messageLens.modify(_ :+ SuperStonksPowered)(_)))
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

  val kitchenDoor: Door = {
    Door(
      i(d("kitchen-room"), "door"),
      SimpleRoomLink(kitchen)
    )
  }

  val apple: Food = {
    Food(
      i("apple"),
      SimpleEatable(onEatExtra = Some(messageLens.modify(_ :+ SuperStonksPowered)(_)))
    )
  }

  val greenApple: Food = {
    Food(
      i(d("green"), "apple"),
      SimpleEatable(onEatExtra = Some(messageLens.modify(_ :+ SuperStonksPowered)(_)))
    )
  }
}
