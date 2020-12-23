package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.{MessagePusher, SQuest}
import zio.{ExitCode, URIO}
import io.github.scalaquest.core.model.impl.SimpleModel._
import io.github.scalaquest.core.model.{Message, Room, SimpleRoom}
import io.github.scalaquest.cli.CLI
import monocle.Lens
import monocle.macros.GenLens

object Model {

  import io.github.scalaquest.core.model.Direction._
  case object GameStarted extends Message
  case object TestMessage extends Message

  def room1: Room = SimpleRoom(
    "room1",
    () =>
      Map(
        NORTH -> room2
      )
  )
  def room2: Room = SimpleRoom(
    "room2",
    () =>
      Map(
        SOUTH -> room1
      )
  )

  val state: SimpleState = SimpleState(
    game = SimpleGame(
      player = SimplePlayer(
        bag = Set(),
        location = room1
      ),
      ended = false
    ),
    messages = Seq(GameStarted)
  )

  def gameLens: Lens[SimpleState, SimpleGame] = GenLens[SimpleState](_.game)
  def playerLens: Lens[SimpleGame, SimplePlayer] = GenLens[SimpleGame](_.player)
  def locationLens: Lens[SimplePlayer, Room] = GenLens[SimplePlayer](_.location)
  def messagesLens: Lens[SimpleState, Seq[Message]] =
    GenLens[SimpleState](_.messages)

  case class Describe(room: Room) extends Message
  case object WentNorth extends Message
  case object WentSouth extends Message

  def game: SQuest[SimpleState] = new SQuest[SimpleState] {
    override def send(
        input: String
    )(state: SimpleState): Either[String, SimpleState] =
      for {
        dir <- input match {
          case "go n" => Right(NORTH)
          case "go s" => Right(SOUTH)
          case _      => Left("Only NORTH and SOUTH directions supported.")
        }
        s <- state.game.player.location neighbors dir match {
          case Some(room) =>
            Right(
              ((gameLens composeLens playerLens composeLens locationLens) set room)(
                state
              )
            )
          case _ => Left("There is no such direction.")
        }
        m <- dir match {
          case NORTH =>
            Right(
              messagesLens
                .set(Seq(WentNorth, Describe(s.game.player.location)))(s)
            )
          case SOUTH =>
            Right(
              messagesLens
                .set(Seq(WentSouth, Describe(s.game.player.location)))(s)
            )
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

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    CLI[SimpleState](
      state,
      game,
      pusher
    ).start.exitCode
}
