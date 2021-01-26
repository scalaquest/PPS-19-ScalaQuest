package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.examples.escaperoom.Config.EatenMessage

object Items {
  import myModel._

  def apple(itemDescription: ItemDescription, itemRef: ItemRef): SimpleFood = {

    SimpleFood(
      itemDescription,
      itemRef,
      SimpleEatable(onEatExtra =
        Option(localState => messageLens.modify(_ :+ EatenMessage)(localState))
      )
    )
  }

}
