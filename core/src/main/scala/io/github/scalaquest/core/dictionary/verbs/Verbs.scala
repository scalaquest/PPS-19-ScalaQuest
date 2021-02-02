package io.github.scalaquest.core.dictionary.verbs

import io.github.scalaquest.core.model.Action

sealed trait BaseVerb {
  def name: String

  def prep: Option[String]
}

final case class Intransitive(name: String, action: Action, prep: Option[String] = None)
  extends BaseVerb
  with ClauseOps
  with PairUtils {

  override def arity: Int = 1
}

final case class Transitive(name: String, action: Action, prep: Option[String] = None)
  extends BaseVerb
  with ClauseOps
  with PairUtils {

  override def arity: Int = 2
}

final case class Ditransitive(
  name: String,
  action: Action,
  prep: Option[String] = None
) extends BaseVerb
  with ClauseOps
  with PairUtils {

  override def arity: Int = 3
}
