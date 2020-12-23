package io.github.scalaquest.cli

import io.github.scalaquest.core.model.{Message, Model}
import io.github.scalaquest.core.{MessagePusher, SQuest}
import zio.console._
import zio.{UIO, ZIO}

trait CLI {
  def start: ZIO[Console, Exception, Unit]
}

object CLI {

  type State = Model#State

  def printNotifications(pusher: MessagePusher)(messages: Seq[Message]): String =
    pusher(messages) reduceOption (_ + "\n" + _) getOrElse ""

  def startGame[S <: State](
      game: SQuest[S],
      pusher: MessagePusher
  )(state: S): ZIO[Console, Exception, Unit] =
    for {
      _ <- putStrLn(printNotifications(pusher)(state.messages))
      _ <- gameLoop(game, pusher)(state)
    } yield ()

  def gameLoop[S <: State](
      game: SQuest[S],
      pusher: MessagePusher
  )(state: S): ZIO[Console, Exception, Unit] =
    for {
      input <- getStrLn
      res <- UIO.succeed((game send input)(state))
      (out, nextState) <- UIO.succeed(res match {
        case Left(err)      => (err, state)
        case Right(updated) => (printNotifications(pusher)(updated.messages), updated)
      })
      _ <- putStrLn(out)
      _ <-
        if (!nextState.game.ended) UIO.succeed(nextState) flatMap gameLoop(game, pusher)
        else ZIO.unit
    } yield ()

  def apply[S <: State](
      state: S,
      game: SQuest[S],
      pusher: MessagePusher
  ): CLI = new CLI {
    override def start: ZIO[Console, Exception, Unit] =
      startGame(game, pusher)(state)
  }
}
