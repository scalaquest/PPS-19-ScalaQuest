package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.examples.escaperoom.Config.EatenMessage

object Items {
  import myModel._

  def apple: SimpleFood = {

    SimpleFood(
      ItemDescription("apple", "red"),
      ItemRef(),
      SimpleEatable(onEatExtra =
        Option(localState => messageLens.modify(_ :+ EatenMessage)(localState))
      )
    )
  }

}
