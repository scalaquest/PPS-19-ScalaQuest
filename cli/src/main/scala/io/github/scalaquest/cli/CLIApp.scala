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
