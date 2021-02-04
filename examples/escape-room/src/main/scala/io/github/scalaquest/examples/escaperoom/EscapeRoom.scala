package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli._
import io.github.scalaquest.core.model.{Message, MessagePusher, RoomRef, StringPusher}
import io.github.scalaquest.core.Game
import io.github.scalaquest.core.pipeline.Pipeline.PipelineBuilder

abstract class GameCLIApp extends CLIApp {

  def pipelineBuilder: PipelineBuilder[State, Model]
  def state: State
  def messagePusher: StringPusher

  def source: String = programFromResource("base.pl")

  def game: Game[Model] = Game.fromModel(model).withPipelineBuilder(pipelineBuilder)

  override def cli: CLI = CLI.fromModel(model).build(state, game, messagePusher)

}

object EscapeRoom extends GameCLIApp {
  import model.{SimplePlayer, SimpleState}

  override def pipelineBuilder: PipelineBuilder[State, Model] =
    defaultPipeline(source, model.SimpleGround)

  override def state: State =
    SimpleState(
      verbToAction,
      model.SimpleMatchState(
        SimplePlayer(Set(), House.kitchen.ref),
        House.refToRoom,
        refToItem
      ),
      Seq.empty
    )

  override def messagePusher: StringPusher = Messages.defaultPusher
}

/*
  val defaultPusher: CommonStringPusher = CommonStringPusher(
    myModel,
    { case SuperStonksPowered =>
      "Became SuperStonks \n" +
        "                                                                                \n                                                                                \n                                              **                  \n                  **..                      ////                  \n              */ //(/*****                 ///////                 \n            ,,,,*/(//((/**              /////////.                \n            ////((#(*,***/.           .*//////////                \n            (#((###(///*/(               ////// .*,               \n            ((######(/#(#,              ./////,                   \n             ((##%%%%#(/                //////                    \n             #((##(#%.                 */////                     \n        &%&&@&&&//(#*.%*               /////*                     \n   &&&&&&&&&&&&@@%//( &%&&&&%&,       //////                      \n  (&&&&&&&&&&&&&&&&&/,&%&&&&&&%%,    ,/////,                      \n   #%&@&&&&&&@@&&&&&&%&%&&&&&&&&%    //////                       \n   ##%&@&&&&&@@&&&%&&&&&&&&&&&&&&&  //////                        \n   .#%%&&&&&@&@@&&&&&&&&&&&&&@&&&&% ,////*                        \n    (#%&&&&&&/@&&&&&&&&&@@&&&@@@&&%%.      "
/*}
)

def game: Game[Model]           = Game builderFrom myModel build pipelineFactory
def messagePusher: StringPusher = defaultPusher
def cli: CLI                    = CLI.builderFrom(myModel).build(state, game, messagePusher)
}

object EscapeRoom extends CLIApp {
  override def cli: CLI = Config.cli
}

 */
