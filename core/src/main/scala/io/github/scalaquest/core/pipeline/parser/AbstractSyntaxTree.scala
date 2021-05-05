/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.parser

import io.github.scalaquest.core.model.ItemDescription

/** Data structure representing the possible output of the parsing operation. */
sealed trait AbstractSyntaxTree

object AbstractSyntaxTree {

  /**
   * Representation of an intransitive sentence.
   * @param verb
   *   the intransitive verb
   * @param prep
   *   the optional preposition used with the verb
   * @param subject
   *   the subject of the verbal predicate
   */
  final case class Intransitive(verb: String, prep: Option[String], subject: String)
    extends AbstractSyntaxTree

  /**
   * Representation of a transitive sentence.
   * @param verb
   *   the transitive verb
   * @param prep
   *   the optional preposition used with the verb
   * @param subject
   *   the subject of the verbal predicate
   * @param obj
   *   the object of the verbal predicate
   */
  final case class Transitive(
    verb: String,
    prep: Option[String],
    subject: String,
    obj: ItemDescription
  ) extends AbstractSyntaxTree

  /**
   * Repesentation of a ditransitive sentence.
   * @param verb
   *   the ditransitive verb
   * @param prep
   *   the optional preposition used with the verb
   * @param subject
   *   the subject of the verbal predicate
   * @param directObj
   *   the direct object of the verbal predicate
   * @param indirectObj
   *   the indirect object of the verbal predicate
   */
  final case class Ditransitive(
    verb: String,
    prep: Option[String],
    subject: String,
    directObj: ItemDescription,
    indirectObj: ItemDescription
  ) extends AbstractSyntaxTree
}
