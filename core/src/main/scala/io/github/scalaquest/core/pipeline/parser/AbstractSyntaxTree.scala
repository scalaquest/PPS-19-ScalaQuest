package io.github.scalaquest.core.pipeline.parser

sealed trait AbstractSyntaxTree

object AbstractSyntaxTree {
  final case class Intransitive(verb: String, subject: String) extends AbstractSyntaxTree

  final case class Transitive(verb: String, subject: String, obj: String) extends AbstractSyntaxTree

  final case class Ditransitive(
    verb: String,
    subject: String,
    directObj: String,
    indirectObj: String
  ) extends AbstractSyntaxTree
}
