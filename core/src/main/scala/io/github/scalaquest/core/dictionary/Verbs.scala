package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.model.Action

// input
sealed trait BaseVerb {
  def name: String

  def preposition: Option[String]
}

final case class Intransitive(name: String, action: Action, preposition: Option[String] = None)
  extends BaseVerb
  with ClauseUtils
  with PairUtils {

  override def arity: Int = 1
}

final case class Transitive(name: String, action: Action, preposition: Option[String] = None)
  extends BaseVerb
  with ClauseUtils
  with PairUtils {

  override def arity: Int = 2
}

final case class Ditransitive(
  name: String,
  action: Action,
  preposition: Option[String] = None
) extends BaseVerb
  with ClauseUtils
  with PairUtils {
  override def arity: Int = 3
}
