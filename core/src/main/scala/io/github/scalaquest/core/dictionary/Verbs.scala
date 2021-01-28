package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.parsing.scalog.{Atom, DCGRule}
import io.github.scalaquest.core.parsing.scalog.dsl._

// input
sealed trait BaseVerb {
  def name: String

  def action: Action
}

final case class Intransitive(name: String, action: Action)
  extends BaseVerb
  with ClauseUtils
  with PairUtils {
  override def clause: DCGRule = iv(atom bReduce 1) --> tokens
}

final case class Transitive(name: String, action: Action)
  extends BaseVerb
  with ClauseUtils
  with PairUtils {
  override def clause: DCGRule = tv(atom bReduce 2) --> tokens
}

final case class Ditransitive(name: String, prep: String, action: Action)
  extends BaseVerb
  with ClauseUtils
  with PairUtils {
  private val _prep            = Atom(prep)
  override def clause: DCGRule = v(3 /: _prep, atom bReduce 3) --> tokens
}

trait Utils extends BaseVerb {
  def escapedName: String = name.replace(" ", "_")
}
