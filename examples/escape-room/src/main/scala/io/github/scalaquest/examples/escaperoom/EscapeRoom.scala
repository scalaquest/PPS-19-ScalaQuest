package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli._
import io.github.scalaquest.core.model.{Message, RoomRef, StringPusher}
import io.github.scalaquest.core.Game
import io.github.scalaquest.core.model.behaviorBased.common.pushing.CommonStringPusher
import io.github.scalaquest.examples.escaperoom.MyPipeline.pipelineFactory

object Config {
  import myModel.{SimplePlayer, SimpleState}

  def player: myModel.SimplePlayer = SimplePlayer(Set(), House.kitchen.ref)

  case object SuperStonksPowered extends Message
  case class Print(msg: String)  extends Message

  case class TextualMessage(msg: String) extends Message
  def rooms: Map[RoomRef, Room] = House.genMap

  def state: SimpleState =
    SimpleState(
      actions,
      myModel.SimpleMatchState(
        player,
        rooms,
        items
      ),
      Seq.empty[Message]
    )

  val defaultPusher: CommonStringPusher = CommonStringPusher(
    myModel,
    { case SuperStonksPowered =>
      "Became SuperStonks \n" +
        "                                                                                \n                                                                                \n                                              **                  \n                  **..                      ////                  \n              *///(/*****                 ///////                 \n            ,,,,*/(//((/**              /////////.                \n            ////((#(*,***/.           .*//////////                \n            (#((###(///*/(               ////// .*,               \n            ((######(/#(#,              ./////,                   \n             ((##%%%%#(/                //////                    \n             #((##(#%.                 */////                     \n        &%&&@&&&//(#*.%*               /////*                     \n   &&&&&&&&&&&&@@%//( &%&&&&%&,       //////                      \n  (&&&&&&&&&&&&&&&&&/,&%&&&&&&%%,    ,/////,                      \n   #%&@&&&&&&@@&&&&&&%&%&&&&&&&&%    //////                       \n   ##%&@&&&&&@@&&&%&&&&&&&&&&&&&&&  //////                        \n   .#%%&&&&&@&@@&&&&&&&&&&&&&@&&&&% ,////*                        \n    (#%&&&&&&/@&&&&&&&&&@@&&&@@@&&%%.      "
    }
  )

  def game: Game[Model]           = Game builderFrom myModel build pipelineFactory
  def messagePusher: StringPusher = defaultPusher
  def cli: CLI                    = CLI.builderFrom(myModel).build(state, game, messagePusher)
}

object EscapeRoom extends CLIApp {
  override def cli: CLI = Config.cli
}
