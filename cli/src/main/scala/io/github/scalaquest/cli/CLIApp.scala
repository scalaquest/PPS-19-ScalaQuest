package io.github.scalaquest.cli

import zio.{ExitCode, URIO}

trait CLIApp extends zio.App {
  def cli: CLI

  final override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = cli.start.exitCode
}
