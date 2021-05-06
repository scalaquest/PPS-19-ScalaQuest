/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.cli

import zio.{ExitCode, URIO}

/**
 * An application that exposes a main method that executes the given `CLI`.
 */
trait CLIApp extends zio.App {

  /** The CLI application to run. */
  def cli: CLI

  final override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = cli.start.exitCode
}
