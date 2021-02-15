package io.github.scalaquest.cli

import io.github.scalaquest.cli.CLI.readLine
import io.github.scalaquest.core.model.RoomRef
import zio.test._
import zio.test.Assertion._
import zio.test.environment._
import zio.test.junit.JUnitRunnableSpec
import CLITestHelper._
import zio.ZIO
import zio.console.Console

import scala.annotation.nowarn

// Suppress weird warning
@nowarn("msg=pure expression")
class CLITest extends JUnitRunnableSpec {

  case class TestCLI(start: ZIO[Console, Exception, Unit]) extends CLI

  def spec =
    suite("CLI tests")(
      suite("CLIApp")(
        testM("it returns code 0 if CLI ends with no errors") {
          val app = new CLIApp {
            override def cli: CLI = TestCLI(ZIO.succeed("ok"))
          }
          for {
            ret <- app.run(List())
          } yield assert(ret.code)(equalTo(0))
        },
        testM("it returns code != 0 if CLI ends with errors") {
          val app = new CLIApp {
            override def cli: CLI = TestCLI(ZIO.fail(new Exception))
          }
          for {
            ret <- app.run(List())
          } yield assert(ret.code)(not(equalTo(0)))
        }
      ),
      suite("readLine")(
        testM("it correctly prints a prompt") {
          for {
            _ <- TestConsole.feedLines("not empty")
            _ <- readLine
            o <- TestConsole.output
          } yield assert(o)(equalTo(Vector("> ")))
        },
        testM("it doesn't accept empty lines") {
          for {
            _ <- TestConsole.feedLines("", "", "not empty")
            _ <- readLine
            o <- TestConsole.output
          } yield assert(o)(equalTo(Vector("> ", "> ", "> ")))
        },
        testM("it returns the read line") {
          for {
            _ <- TestConsole.feedLines("something")
            i <- readLine
          } yield assert(i)(equalTo("something"))
        }
      ),
      suite("CLI start")(
        testM("it correctly prints the startup message") {
          val s0 = state.copy(messages = Seq(testMessage1))
          val p  = messagePusher

          val cli = CLI.builderFrom(model).build(s0, gameRight, p)
          for {
            _ <- TestConsole.feedLines("end")
            _ <- cli.start
          } yield assert(p.messages)(equalTo(List(testMessage1)))
        },
        testM("it correctly handles states update") {
          val p    = messagePusher
          val game = gameRight
          val cli  = CLI.builderFrom(model).build(state, game, p)

          for {
            _ <- TestConsole.feedLines("fake command", "end")
            _ <- cli.start
          } yield {
            assert(game.states)(equalTo(List(state, state.copy(_location = RoomRef("2"))))) &&
            assert(p.messages)(equalTo(List(testMessage2)))
          }

        },
        testM("it correctly handles pipeline errors") {
          val p    = messagePusher
          val game = gameLeft
          val cli  = CLI.builderFrom(model).build(state, game, p)

          for {
            _ <- TestConsole.feedLines("some errors", "end")
            _ <- cli.start
          } yield {
            assert(game.states)(equalTo(List(state, state))) &&
            assert(p.messages)(equalTo(List()))
          }
        }
      )
    )
}
