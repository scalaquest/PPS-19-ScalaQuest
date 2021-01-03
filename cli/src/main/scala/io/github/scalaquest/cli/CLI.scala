package io.github.scalaquest.cli

import io.github.scalaquest.core.model.{Message, Model}
import io.github.scalaquest.core.{MessagePusher, Game}
import zio.console._
import zio.{UIO, ZIO}

trait CLI {
  def start: ZIO[Console, Exception, Unit]
}

object CLI {

  def fromModel[M <: Model](implicit model: M) = new CLIFromModel[M](model)

  class CLIFromModel[M <: Model](val model: M) {
    type S = model.S

    private def printNotifications(pusher: MessagePusher)(messages: Seq[Message]): String =
      pusher(messages) reduceOption (_ + "\n" + _) getOrElse ""

    private def gameLoop(game: Game[model.type], pusher: MessagePusher)(state: S): ZIO[Console, Exception, Unit] =
      for {
        input <- getStrLn
        res   <- UIO.succeed((game send input)(state))
        (out, nextState) <- UIO.succeed(res match {
          case Left(err)      => (err, state)
          case Right(updated) => (printNotifications(pusher)(updated.messages), updated)
        })
        _ <- putStrLn(out)
        _ <- if (!nextState.game.ended) UIO.succeed(nextState) flatMap gameLoop(game, pusher) else ZIO.unit
      } yield ()

    def build(state: S, game: Game[model.type], pusher: MessagePusher): CLI =
      new CLI() {

        override def start: ZIO[Console, Exception, Unit] =
          for {
            _ <- putStrLn(printNotifications(pusher)(state.messages))
            _ <- gameLoop(game, pusher)(state)
          } yield ()
      }
  }

}
