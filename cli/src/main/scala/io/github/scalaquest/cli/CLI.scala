/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.cli

import io.github.scalaquest.core.Game
import io.github.scalaquest.core.model.{Message, MessagePusher, Model}
import zio.console._
import zio.{IO, UIO, ZIO}

import java.io.IOException
import scala.io.AnsiColor

/**
 * An generic command line application that takes no arguments.
 */
trait CLI {
  def start: ZIO[Console, IOException, Unit]
}

object CLI {

  /** A builder function to create a `CLI` using dependent types on the `Model` instance. */
  def builderFrom[M <: Model](implicit model: M) = new CLIBuilder[M](model)

  /** An effect that prints a prompt and asks for a command that cannot be empty. */
  def readLine: ZIO[Console, IOException, Command] =
    for {
      _   <- putStr("> ") *> putStr("\u001B[3m") *> putStr(AnsiColor.GREEN)
      in  <- getStrLn.map(Command.make)
      _   <- putStr(AnsiColor.RESET)
      cmd <- ZIO.succeed(in).someOrElseM(readLine)
    } yield cmd;

  class CLIBuilder[M <: Model](val model: M) {

    /**
     * Creates a `CLI` with the given parameters.
     * @param state
     *   the initial state
     * @param game
     *   the game to send the commands to
     * @param pusher
     *   the interpreter of the messages
     * @param initialMessages
     *   optional initial messages
     * @return
     *   the command line application
     */
    def build(
      state: model.S,
      game: Game[model.type],
      pusher: MessagePusher[String],
      initialMessages: Seq[Message] = Seq()
    ): CLI =
      new CLI() {

        override def start: ZIO[Console, IOException, Unit] =
          for {
            _ <-
              if (initialMessages.nonEmpty) putStrLn(pusher push initialMessages)
              else ZIO.unit
            _ <- gameLoop(game, pusher)(state)
          } yield ()
      }

    private def saveState(
      path: String,
      state: model.S
    ): IO[Either[UnsupportedOperationException, IOException], Unit] = {
      IO.fromFuture { _ =>
        model.serializer.get.write(path, state)
      }.mapError {
        case _: NoSuchElementException => Left(new UnsupportedOperationException)
        case e: IOException            => Right(e)
        case t: Throwable              => throw t
      }
    }

    private def loadState(
      path: String
    ): IO[Either[UnsupportedOperationException, IOException], model.S] = {
      IO
        .fromFuture { _ =>
          model.serializer.get.read(path)
        }
        .mapError {
          case _: NoSuchElementException => Left(new UnsupportedOperationException)
          case e: IOException            => Right(e)
          case t: Throwable              => throw t
        }
    }

    private def gameLoop(
      game: Game[model.type],
      pusher: MessagePusher[String]
    )(state: model.S): ZIO[Console, IOException, Unit] = {

      def onCommand(input: String): ZIO[Console, IOException, Unit] = {
        val loop = for {
          pipelineRes <- UIO.succeed((game send input)(state))
          (output, updState) <- IO
            .succeed(pipelineRes match {
              case Left(err)                   => (err, state)
              case Right((updState, messages)) => (pusher push messages, updState)
            })
          _ <- putStrLn(output)
          _ <-
            if (updState.ended) ZIO.unit
            else gameLoop(game, pusher)(updState)
        } yield ()
        loop.refineToOrDie[IOException]
      }

      def onMetaCommand(input: String): ZIO[Console, IOException, Unit] =
        for {
          nextState <- input match {
            case Command.Parser("save", path) =>
              for {
                saveResult <- saveState(path, state).either
                _ <- saveResult match {
                  case Left(Left(_)) =>
                    putStrLn("Operation not supported.")
                  case Left(Right(e)) =>
                    putStrLn(s"Couldn't save game: ${e.getMessage}")
                  case Right(_) =>
                    putStrLn("Saved game successfully!")
                }
              } yield None
            case Command.Parser("load", path) =>
              for {
                loadResult <- loadState(path).either
                s <- loadResult match {
                  case Left(Left(_)) =>
                    putStrLn("Operation not supported.") *> ZIO.none
                  case Left(Right(e)) =>
                    putStrLn(s"Couldn't load game: ${e.getMessage}") *> ZIO.none
                  case Right(updatedState) =>
                    putStrLn("Loaded game save successfully!").as(updatedState).asSome
                }
              } yield s
            case Command.Parser("save", _*) =>
              putStrLn("Usage: :save <file>") *> ZIO.none
            case Command.Parser("load", _*) =>
              putStrLn("Usage: :load <file>") *> ZIO.none
            case _ =>
              putStrLn("Error: Unrecognized command \"" + input + "\"") *> ZIO.none
          }
          _ <- gameLoop(game, pusher)(nextState getOrElse state)
        } yield ()

      for {
        command <- readLine
        _ <- command match {
          case GameCommand(body) => onCommand(body)
          case MetaCommand(body) => onMetaCommand(body)
        }
      } yield ()
    }
  }

}
