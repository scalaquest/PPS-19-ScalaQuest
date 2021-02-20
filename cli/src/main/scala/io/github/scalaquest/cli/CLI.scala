package io.github.scalaquest.cli

import io.github.scalaquest.core.Game
import io.github.scalaquest.core.model.{Message, MessagePusher, Model}
import zio.console._
import zio.{ExitCode, UIO, URIO, ZIO}

import java.io.IOException

trait CLIApp extends zio.App {
  def cli: CLI

  final override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = cli.start.exitCode
}

trait CLI {
  def start: ZIO[Console, Exception, Unit]
}

object CLI {

  def builderFrom[M <: Model](implicit model: M) = new CLIBuilder[M](model)

  def readLine: ZIO[Console, IOException, String] =
    for {
      _  <- putStr("> ")
      i  <- getStrLn
      i2 <- if (i == "") readLine else ZIO.succeed(i)
    } yield i2;

  class CLIBuilder[M <: Model](val model: M) {

    private def gameLoop(
      game: Game[model.type],
      pusher: MessagePusher[String]
    )(startState: model.S): ZIO[Console, Exception, Unit] =
      for {
        input       <- readLine
        pipelineRes <- UIO.succeed((game send input)(startState))
        (output, updState) <- UIO.succeed(pipelineRes match {
          case Left(err)                   => (err, startState)
          case Right((updState, messages)) => (pusher push messages, updState)
        })
        _ <- putStrLn(output)
        _ <-
          if (updState.ended) ZIO.unit
          else UIO.succeed(updState) flatMap gameLoop(game, pusher)

      } yield ()

    def build(
      state: model.S,
      game: Game[model.type],
      pusher: MessagePusher[String],
      initialMessages: Seq[Message] = Seq()
    ): CLI =
      new CLI() {

        override def start: ZIO[Console, Exception, Unit] =
          for {
            _ <-
              if (initialMessages.nonEmpty) putStrLn(pusher push initialMessages)
              else ZIO.unit
            _ <- gameLoop(game, pusher)(state)
          } yield ()
      }
  }

}
