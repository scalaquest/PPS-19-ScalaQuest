package io.github.scalaquest.core.pipeline.parser

/** Data structure representing the possible output of the parsing operation. */
sealed trait AbstractSyntaxTree

object AbstractSyntaxTree {

  /**
   * Representation of an intransitive sentence.
   * @param verb
   *   the intransitive verb
   * @param subject
   *   the subject of the verbal predicate
   */
  final case class Intransitive(verb: String, subject: String) extends AbstractSyntaxTree

  /**
   * Representation of a transitive sentence.
   * @param verb
   *   the transitive verb
   * @param subject
   *   the subject of the verbal predicate
   * @param obj
   *   the object of the verbal predicate
   */
  final case class Transitive(verb: String, subject: String, obj: String) extends AbstractSyntaxTree

  /**
   * Repesentation of a ditransitive sentence.
   * @param verb
   *   the ditransitive verb
   * @param subject
   *   the subject of the verbal predicate
   * @param directObj
   *   the direct object of the verbal predicate
   * @param indirectObj
   *   the indirect object of the verbal predicate
   */
  final case class Ditransitive(
    verb: String,
    subject: String,
    directObj: String,
    indirectObj: String
  ) extends AbstractSyntaxTree
}
