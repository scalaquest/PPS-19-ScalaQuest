package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli._
import io.github.scalaquest.core.{Game, MessagePusher}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel

object Config {
  val myModel: SimpleModel.type = SimpleModel
  import myModel._
  val player = SimplePlayer(Set(), House.kitchen)

  val state: myModel.SimpleState = SimpleState(
    actions,
    SimpleMatchState(
      player,
      ended = false,
      Map(),
      Set()
    ),
    Seq()
  )
  val game: Game[myModel.type]     = ???
  val messagePusher: MessagePusher = ???
  val cli: CLI                     = CLI.fromModel(myModel).build(state, game, messagePusher)
}

object EscapeRoom extends CLIApp {
  override def cli: CLI = Config.cli
}
