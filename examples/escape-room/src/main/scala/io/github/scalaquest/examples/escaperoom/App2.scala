package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.{Game, MessagePusher}
import zio.{ExitCode, URIO}
import io.github.scalaquest.core.model.{Message, Room}
import io.github.scalaquest.cli.CLI
import io.github.scalaquest.core.model.Room.Direction
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  SimpleMatchState,
  SimplePlayer,
  SimpleState
}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import monocle.Lens
import monocle.macros.GenLens

object Model {

  case object GameStarted extends Message
  case object TestMessage extends Message

  def room1: Room = Room("room1", Map(Direction.North -> room2))
  def room2: Room = Room("room2", Map(Direction.South -> room1))

  val model: SimpleModel.type = SimpleModel

  val state: SimpleState = ???

  def gameLens: Lens[SimpleState, SimpleMatchState]    = GenLens[SimpleState](_.matchState)
  def playerLens: Lens[SimpleMatchState, SimplePlayer] = GenLens[SimpleMatchState](_.player)
  def locationLens: Lens[SimplePlayer, Room]           = GenLens[SimplePlayer](_.location)
  def messagesLens: Lens[SimpleState, Seq[Message]]    = GenLens[SimpleState](_.messages)

  case class Describe(room: Room) extends Message
  case object WentNorth           extends Message
  case object WentSouth           extends Message

  def game: Game[SimpleModel.type] =
    new Game(model) {

      override def send(input: String)(state: SimpleState): Either[String, SimpleState] =
        for {
          dir <- input match {
            case "go n" => Right(Direction.North)
            case "go s" => Right(Direction.South)
            case _      => Left("Only NORTH and SOUTH directions supported.")
          }
          s <- state.matchState.player.location neighbors dir match {
            case Some(room) =>
              Right(((gameLens composeLens playerLens composeLens locationLens) set room)(state))
            case _ => Left("There is no such direction.")
          }
          m <- dir match {
            case Direction.North =>
              Right(messagesLens.set(Seq(WentNorth, Describe(s.matchState.player.location)))(s))
            case Direction.South =>
              Right(messagesLens.set(Seq(WentSouth, Describe(s.matchState.player.location)))(s))
            case _ => Left("There is no such direction.")
          }
        } yield m
    }

  def pusher: MessagePusher =
    (notifications: Seq[Message]) =>
      notifications map {
        case GameStarted => "Game started!"
        case WentNorth   => "Moved north"
        case WentSouth   => "Moved south"
        case _           => "Generic notification"
      }
}

object App2 extends zio.App {
  import Model._
  implicit val model: SimpleModel.type = SimpleModel

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    CLI.fromModel(model).build(state, game, pusher).start.exitCode

}
