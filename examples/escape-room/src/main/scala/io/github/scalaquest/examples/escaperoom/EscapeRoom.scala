package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli._
import io.github.scalaquest.core.model.{Model, SimpleRoom}
import io.github.scalaquest.core.{Game, MessagePusher}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel

object Config {
  val myModel: SimpleModel.type = SimpleModel
  import myModel._
  // val player = SimplePlayer(Set(), )
  SimpleMatchState
  val state: myModel.SimpleState   = ???
  val game: Game[myModel.type]     = ???
  val messagePusher: MessagePusher = ???
  val cli: CLI                     = CLI.fromModel(myModel).build(state, game, messagePusher)
}

object EscapeRoom extends CLIApp(Config.cli)
