package io.github.scalaquest.cli

import io.github.scalaquest.core.Game
import io.github.scalaquest.core.model.Model

abstract class TestGame[M <: Model](override val model: M) extends Game[M](model) {

  var states: List[model.S] = List()

  def returns(input: String): Either[String, model.S]

  override def send(input: String)(
    state: model.S
  ): Either[String, model.S] = {
    states = states :+ state
    returns(input)
  }
}
