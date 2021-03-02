package io.github.scalaquest.cli

import io.github.scalaquest.core.Game
import io.github.scalaquest.core.model.{Message, MessagePusher, Model}
import zio.console._
import zio.{IO, UIO, ZIO}

import java.io.IOException

trait CLI {
  def start: ZIO[Console, Throwable, Unit]
}

object CLI {

  def builderFrom[M <: Model](implicit model: M) = new CLIBuilder[M](model)

  def readLine: ZIO[Console, IOException, Command] =
    for {
      _  <- putStr("> ")
      i  <- getStrLn.map(Command.make)
      i2 <- ZIO.succeed(i).flatMap(x => x.map(ZIO.succeed(_)).getOrElse(readLine))
    } yield i2;

  class CLIBuilder[M <: Model](val model: M) {

    def saveState(path: String, state: model.S): IO[IOException, Unit] = {
      IO.fromFuture { implicit ec =>
        model.serializer.write(path, state)
      }.refineToOrDie[IOException]
    }

    def loadState(path: String): IO[IOException, model.S] = {
      IO.fromFuture { implicit ec =>
        model.serializer.read(path)
      }.refineToOrDie[IOException]
    }

    private def gameLoop(
      game: Game[model.type],
      pusher: MessagePusher[String]
    )(state: model.S): ZIO[Console, IOException, Unit] = {

//       def saveState(path: String): IO[IOException, Unit] = {
//         import upickle.default.{write => serialize, readwriter, ReadWriter}
//         implicit val stateReadWrite: ReadWriter[model.S] =
//           readwriter[String].bimap[model.S](_.location.name, _ => state)
//         IO.effect {
//           val writer = new OutputStreamWriter(new FileOutputStream(path))
//           writer.write(serialize(state))
//           writer.close()
//         }.refineToOrDie[IOException]
//       }
//
//       def loadState(path: String): IO[IOException, model.S] = {
//         import upickle.default.{readwriter, ReadWriter, read => deserialize}
//         implicit val stateReadWrite: ReadWriter[model.S] =
//           readwriter[String].bimap[model.S](_.location.name, _ => state)
//
//         IO.effect {
//           val reader = new BufferedInputStream(new FileInputStream(path))
//           val state = LazyList
//             .continually(reader.read())
//             .takeWhile(_ != -1)
//             .map(_.toChar)
//             .mkString
//           deserialize(state)
//         }.refineToOrDie[IOException]
//       }

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

      def onMetaCommand(input: String): ZIO[Console, IOException, Unit] = {
        for {
          nextState <- input match {
            case Command.Parser("save", path) =>
              for {
                saveResult <- saveState(path, state).either
                _ <- saveResult match {
                  case Left(e) =>
                    putStrLn(s"Couldn't save game: ${e.getMessage}")
                  case Right(_) =>
                    putStrLn("Saved game successfully!")
                }
              } yield None
            case Command.Parser("save", _*) =>
              putStrLn("Usage: :save <file>") *> ZIO.none
            case Command.Parser("load", path) =>
              for {
                loadResult <- loadState(path).either
                s <- loadResult match {
                  case Left(e) =>
                    putStrLn(s"Couldn't load game: ${e.getMessage}") *> ZIO.none
                  case Right(updatedState) =>
                    putStrLn("Loaded game save successfully!").as(updatedState).asSome
                }
              } yield s
            case Command.Parser("load", _*) =>
              putStrLn("Usage: :load <file>") *> ZIO.none
            case _ =>
              putStrLn("Error: Unrecognized command \"" + input + "\"") *> ZIO.none
          }
          _ <- gameLoop(game, pusher)(nextState getOrElse state)
        } yield ()
      }

      for {
        command <- readLine
        _ <- command match {
          case GameCommand(body) => onCommand(body)
          case MetaCommand(body) => onMetaCommand(body)
        }
      } yield ()
    }

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
  }

}
