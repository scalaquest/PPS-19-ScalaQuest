package io.github.scalaquest.cli

sealed trait Command
case class GameCommand(body: String) extends Command
case class MetaCommand(body: String) extends Command

object Command {

  def make(body: String): Option[Command] = {
    body.splitAt(1) match {
      case (fst, rest) if (fst + rest).isBlank => None
      case (":", meta)                         => Some(MetaCommand(meta))
      case (fst, rest)                         => Some(GameCommand(fst + rest))
    }
  }

  object Parser {

    def unapplySeq(body: String): Option[Seq[String]] = {
      val matcher = raw"(\S+)".r
      Option(matcher.findAllMatchIn(body).map(_.matched).toList)
        .filter(_.nonEmpty)
    }
  }
}
