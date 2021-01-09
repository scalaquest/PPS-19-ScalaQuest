package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.{Game, MessagePusher}
import zio.{ExitCode, URIO}
import io.github.scalaquest.core.model.{Message, Room}
import io.github.scalaquest.cli.CLI
import io.github.scalaquest.core.model.std.{StdModel, StdRoom}
import io.github.scalaquest.core.model.std.StdModel.{StdGameState, StdPlayer, StdState}
import monocle.Lens
import monocle.macros.GenLens

object Model {

  import io.github.scalaquest.core.model.Direction._
  case object GameStarted extends Message
  case object TestMessage extends Message

  def room1: Room = StdRoom("room1", () => Map(NORTH -> room2))
  def room2: Room = StdRoom("room2", () => Map(SOUTH -> room1))

  val model: StdModel.type = StdModel

  val state: StdState = ???

  def gameLens: Lens[StdState, StdGameState]     = GenLens[StdState](_.game)
  def playerLens: Lens[StdGameState, StdPlayer]  = GenLens[StdGameState](_.player)
  def locationLens: Lens[StdPlayer, Room]        = GenLens[StdPlayer](_.location)
  def messagesLens: Lens[StdState, Seq[Message]] = GenLens[StdState](_.messages)

  case class Describe(room: Room) extends Message
  case object WentNorth           extends Message
  case object WentSouth           extends Message

  def game: Game[StdModel.type] =
    new Game(model) {

      override def send(input: String)(state: StdState): Either[String, StdState] =
        for {
          dir <- input match {
            case "go n" => Right(NORTH)
            case "go s" => Right(SOUTH)
            case _      => Left("Only NORTH and SOUTH directions supported.")
          }
          s <- state.game.player.location neighbors dir match {
            case Some(room) => Right(((gameLens composeLens playerLens composeLens locationLens) set room)(state))
            case _          => Left("There is no such direction.")
          }
          m <- dir match {
            case NORTH => Right(messagesLens.set(Seq(WentNorth, Describe(s.game.player.location)))(s))
            case SOUTH => Right(messagesLens.set(Seq(WentSouth, Describe(s.game.player.location)))(s))
          }
        } yield m
    }

  def pusher: MessagePusher =
    (notifications: Seq[Message]) =>
      notifications map {
        case GameStarted    => "Game started!"
        case Describe(room) => room.describe
        case WentNorth      => "Moved north"
        case WentSouth      => "Moved south"
        case _              => "Generic notification"
      }
}

object App2 extends zio.App {
  import Model._
  implicit val model: StdModel.type = StdModel

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    CLI.fromModel(model).build(state, game, pusher).start.exitCode

}
