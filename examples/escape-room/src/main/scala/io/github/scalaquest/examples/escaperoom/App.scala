package io.github.scalaquest.examples.escaperoom

trait AppObj

// trying to import code from core and cli module,
// only acceding the core module

import io.github.scalaquest.cli._
import io.github.scalaquest.core._

trait EscapeRoom

object App {
  def main(args: Array[String]): Unit = println("Hello Viroli!")
}
