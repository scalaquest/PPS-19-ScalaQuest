package io.github.scalaquest.cli

import io.github.scalaquest.core.model.{Model, StringPusher}
import io.github.scalaquest.core.Game
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

  class CLIBuilder[M <: Model](val model: M) {

    private def readLine: ZIO[Console, IOException, String] =
      for {
        _  <- putStr("> ")
        i  <- getStrLn
        i2 <- if (i == "") readLine else ZIO.succeed(i)
      } yield i2;

    private def gameLoop(
      game: Game[model.type],
      pusher: StringPusher
    )(startState: model.S): ZIO[Console, Exception, Unit] =
      for {
        input       <- readLine
        pipelineRes <- UIO.succeed((game send input)(startState))
        (output, updState) <- UIO.succeed(pipelineRes match {
          case Left(err)       => (err, startState)
          case Right(updState) => (pusher push updState.messages, updState)
        })
        _         <- putStrLn(output)
        nextState <- UIO.succeed(model.messageLens.set(Seq())(updState))
        _ <-
          if (nextState.ended) ZIO.unit
          else UIO.succeed(nextState) flatMap gameLoop(game, pusher)

      } yield ()

    def build(state: model.S, game: Game[model.type], pusher: StringPusher): CLI =
      new CLI() {

        override def start: ZIO[Console, Exception, Unit] =
          for {
            _ <- putStrLn(pusher push state.messages)
            _ <- gameLoop(game, pusher)(state)
          } yield ()
      }
  }

}
