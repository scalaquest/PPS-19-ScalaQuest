package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.{Game, MessagePusher}
import zio.{ExitCode, URIO}
import io.github.scalaquest.core.model.{Message, Room}
import io.github.scalaquest.cli.CLI
import io.github.scalaquest.core.model.default.{DefaultModel, DefaultRoom}
import io.github.scalaquest.core.model.default.DefaultModel.{DefaultGameState, DefaultPlayer, DefaultState}
import monocle.Lens
import monocle.macros.GenLens

object Model {

  import io.github.scalaquest.core.model.Direction._
  case object GameStarted extends Message
  case object TestMessage extends Message

  def room1: Room = DefaultRoom("room1", () => Map(NORTH -> room2))
  def room2: Room = DefaultRoom("room2", () => Map(SOUTH -> room1))

  val model: DefaultModel.type = DefaultModel

  val state: DefaultState = ???

  def gameLens: Lens[DefaultState, DefaultGameState]    = GenLens[DefaultState](_.game)
  def playerLens: Lens[DefaultGameState, DefaultPlayer] = GenLens[DefaultGameState](_.player)
  def locationLens: Lens[DefaultPlayer, Room]           = GenLens[DefaultPlayer](_.location)
  def messagesLens: Lens[DefaultState, Seq[Message]]    = GenLens[DefaultState](_.messages)

  case class Describe(room: Room) extends Message
  case object WentNorth           extends Message
  case object WentSouth           extends Message

  def game: Game[DefaultModel.type] =
    new Game(model) {

      override def send(input: String)(state: DefaultState): Either[String, DefaultState] =
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
  implicit val model: DefaultModel.type = DefaultModel

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    CLI.fromModel(model).build(state, game, pusher).start.exitCode

}
