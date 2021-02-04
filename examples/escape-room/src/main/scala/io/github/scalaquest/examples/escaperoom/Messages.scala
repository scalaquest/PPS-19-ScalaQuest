package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.Message
import io.github.scalaquest.core.model.behaviorBased.common.pushing.CommonStringPusher

object Messages {
  case object SuperStonksPowered extends Message
  case class Print(msg: String)  extends Message

  case class TextualMessage(msg: String) extends Message

  val defaultPusher: CommonStringPusher = CommonStringPusher(
    model,
    { case SuperStonksPowered =>
      "Became SuperStonks \n" +
        "                                                                                \n                                                                                \n                                              **                  \n                  **..                      ////                  \n              */ //(/*****                 ///////                 \n            ,,,,*/(//((/**              /////////.                \n            ////((#(*,***/.           .*//////////                \n            (#((###(///*/(               ////// .*,               \n            ((######(/#(#,              ./////,                   \n             ((##%%%%#(/                //////                    \n             #((##(#%.                 */////                     \n        &%&&@&&&//(#*.%*               /////*                     \n   &&&&&&&&&&&&@@%//( &%&&&&%&,       //////                      \n  (&&&&&&&&&&&&&&&&&/,&%&&&&&&%%,    ,/////,                      \n   #%&@&&&&&&@@&&&&&&%&%&&&&&&&&%    //////                       \n   ##%&@&&&&&@@&&&%&&&&&&&&&&&&&&&  //////                        \n   .#%%&&&&&@&@@&&&&&&&&&&&&&@&&&&% ,////*                        \n    (#%&&&&&&/@&&&&&&&&&@@&&&@@@&&%%.      "
    }
  )

}
