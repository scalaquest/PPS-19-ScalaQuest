package io.github.scalaquest.core.dictionary.verbs

import io.github.scalaquest.core.model.Action

abstract class BaseVerb {
  def name: String

  def prep: Option[String]
}

// Trying to avoid to repeat all the mixed in traits for 3 times
sealed abstract class EnhancedVerb extends BaseVerb with ClauseOps with Meaning

final case class Intransitive(name: String, action: Action, prep: Option[String] = None)
  extends EnhancedVerb {

  override def arity: Int = 1
}

final case class Transitive(name: String, action: Action, prep: Option[String] = None)
  extends EnhancedVerb {

  override def arity: Int = 2
}

final case class Ditransitive(
  name: String,
  action: Action,
  prep: Option[String] = None
) extends EnhancedVerb {

  override def arity: Int = 3
}
