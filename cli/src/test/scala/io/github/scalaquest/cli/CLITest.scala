/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.cli

import io.github.scalaquest.cli.CLI.readLine
import io.github.scalaquest.cli.CLITestHelper._
import io.github.scalaquest.core.model.RoomRef
import zio.ZIO
import zio.console.Console
import zio.test.Assertion._
import zio.test._
import zio.test.environment._
import zio.test.junit.JUnitRunnableSpec

import java.io.IOException
import scala.annotation.nowarn

class CLITest extends JUnitRunnableSpec {

  case class TestCLI(start: ZIO[Console, IOException, Unit]) extends CLI

  def spec =
    suite("CLI tests")(
      suite("CLIApp")(
        testM("it returns code 0 if CLI ends with no errors") {
          @nowarn("msg=pure expression") // this generates a warning for some unknown reason
          val app = new CLIApp {
            override def cli: CLI = TestCLI(ZIO.succeed("ok"))
          }
          for {
            ret <- app.run(List())
          } yield assert(ret.code)(equalTo(0))
        },
        testM("it returns code != 0 if CLI ends with errors") {
          val app = new CLIApp {
            override def cli: CLI = TestCLI(ZIO.fail(new IOException))
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
          } yield assert(o)(contains("> "))
        },
        testM("it doesn't accept empty lines") {
          for {
            _ <- TestConsole.feedLines("", "", "not empty")
            _ <- readLine
            o <- TestConsole.output
          } yield assert(o)(hasIntersection(Vector("> ", "> ", "> "))(hasSize(equalTo(3))))
        },
        testM("it returns the read line") {
          for {
            _ <- TestConsole.feedLines("something")
            i <- readLine
          } yield assert(i)(equalTo(GameCommand("something")))
        },
        testM("it returns a meta command") {
          for {
            _ <- TestConsole.feedLines(":something")
            i <- readLine
          } yield assert(i)(equalTo(MetaCommand("something")))
        }
      ),
      suite("Metacommands")(
        testM("it correctly handles save with the wrong arguments number") {
          val p   = messagePusher
          val cli = CLI.builderFrom(model).build(state, gameRight, p, Seq())
          for {
            _ <- TestConsole.feedLines(":save", "end")
            _ <- cli.start
            o <- TestConsole.output
          } yield assert(o)(hasAt(4)(containsString("Usage")))
        },
        testM("it correctly handles load with the wrong arguments number") {
          val p   = messagePusher
          val cli = CLI.builderFrom(model).build(state, gameRight, p, Seq())
          for {
            _ <- TestConsole.feedLines(":load", "end")
            _ <- cli.start
            o <- TestConsole.output
          } yield assert(o)(hasAt(4)(containsString("Usage")))
        },
        testM("it correctly handles wrong commands") {
          val p   = messagePusher
          val cli = CLI.builderFrom(model).build(state, gameRight, p, Seq())
          for {
            _ <- TestConsole.feedLines(":wrong-command", "end")
            _ <- cli.start
            o <- TestConsole.output
          } yield assert(o)(hasAt(4)(containsString("Unrecognized")))
        },
        testM("it correctly handles operation not supported") {
          val p   = messagePusher
          val cli = CLI.builderFrom(model).build(state, gameRight, p, Seq())
          val t1: ZIO[TestConsole with Console, IOException, TestResult] = for {
            _ <- TestConsole.feedLines(":save somepath", "end")
            _ <- cli.start
            o <- TestConsole.output
          } yield assert(o)(hasAt(4)(containsString("not supported")))
          val t2: ZIO[TestConsole with Console, IOException, TestResult] = for {
            _ <- TestConsole.feedLines(":load somepath", "end")
            _ <- cli.start
            o <- TestConsole.output
          } yield assert(o)(hasAt(4)(containsString("not supported")))
          t1 *> t2
        }
      ),
      suite("CLI start")(
        testM("it correctly prints the startup message") {
          val p = messagePusher

          val cli = CLI.builderFrom(model).build(state, gameRight, p, Seq(testMessage1))
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
