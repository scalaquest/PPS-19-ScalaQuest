package io.github.scalaquest.cli

import io.github.scalaquest.core.model.{Message, Model, StringPusher}
import io.github.scalaquest.core.Game
import monocle.Lens
import monocle.macros.GenLens
import zio.console._
import zio.{ExitCode, UIO, URIO, ZIO}

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

    private def gameLoop(
      game: Game[model.type],
      pusher: StringPusher
    )(startState: model.S): ZIO[Console, Exception, Unit] =
      for {
        input       <- getStrLn
        pipelineRes <- UIO.succeed((game send input)(startState))
        (output, updState) <- UIO.succeed(pipelineRes match {
          case Left(err)       => (err, startState)
          case Right(updState) => (pusher push updState.messages, updState)
        })
        _         <- putStrLn(output)
        nextState <- UIO.succeed(model.messageLens.set(Seq())(updState))
        _ <-
          if (nextState.matchState.ended) ZIO.unit
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
