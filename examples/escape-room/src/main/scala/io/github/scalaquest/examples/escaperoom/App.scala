package io.github.scalaquest.examples.escaperoom

trait App

// trying to import code from core and cli module,
// only acceding the core module

import io.github.scalaquest.cli._
import io.github.scalaquest.core._

case class TestImportCLI() extends CLI
case class TestImportCore() extends Core
trait EscapeRoom

object App {
  def main(args: Array[String]): Unit = {
    println("Hello Viroli!")
  }
}