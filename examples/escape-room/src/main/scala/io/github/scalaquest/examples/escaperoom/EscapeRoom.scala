package io.github.scalaquest.examples.escaperoom

// trying to import code from core and cli module,
// only acceding the core module

import io.github.scalaquest.cli._
import io.github.scalaquest.core._
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import zio.{ExitCode, URIO}

trait EscapeRoom

object App extends zio.App {
  val model: SimpleModel.type                                    = SimpleModel
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = CLI.fromModel(model)
}
