package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

object Items {
  import myModel._

  def apple: SimpleFood =
    SimpleFood(
      ItemDescription("apple", "red"),
      ItemRef(),
      SimpleEatable(onEatExtra = state: S => state.copy(messages = Set(EatenMessage)))
    )
}
