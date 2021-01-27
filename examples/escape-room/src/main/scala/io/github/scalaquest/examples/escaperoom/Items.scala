package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.examples.escaperoom.Config.SuperStonksPowered

object Items {
  import myModel._

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
