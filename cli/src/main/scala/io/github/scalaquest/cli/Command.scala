/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.cli

/** A wrapper for the user input. */
sealed trait Command

/** A command intended to the game. */
case class GameCommand(body: String) extends Command

/** A command intended not to be passed to the game. */
case class MetaCommand(body: String) extends Command

object Command {

  /** It creates a `Command` given its `String` representation. */
  def make(body: String): Option[Command] = {
    body.splitAt(1) match {
      case (fst, rest) if (fst + rest).isBlank => None
      case (":", meta)                         => Some(MetaCommand(meta))
      case (fst, rest)                         => Some(GameCommand(fst + rest))
    }
  }

  /**
   * An elementary parser for cli meta commands. It gives an helper function to pattern match
   * against `String` representation of commands.
   */
  object Parser {

    def unapplySeq(body: String): Option[Seq[String]] = {
      val matcher = raw"(\S+)".r
      Option(matcher.findAllMatchIn(body).map(_.matched).toList)
        .filter(_.nonEmpty)
    }
  }
}
